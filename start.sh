#!/bin/bash

check_emqx_client() {
  if ! command -v mqtt &>/dev/null; then
    echo "EMQX client not found, installing..."

    if [[ "$OSTYPE" == "linux-gnu"* ]]; then
      curl -LO https://www.emqx.com/zh/downloads/MQTTX/v1.8.0/mqttx-cli-linux-x64
      sudo install ./mqttx-cli-linux-x64 /usr/local/bin/mqtt
    elif [[ "$OSTYPE" == "darwin"* ]]; then
      curl -LO https://www.emqx.com/zh/downloads/MQTTX/v1.8.0/mqttx-cli-macos-arm64
      sudo install ./mqttx-cli-macos-arm64 /usr/local/bin/mqtt
    elif [[ "$OSTYPE" == "msys" ]]; then
      echo "EMQX client installation on Windows is not supported via this script."
      exit 1
    else
      echo "Unsupported OS: $OSTYPE"
      exit 1
    fi

    if ! command -v mqtt &>/dev/null; then
      echo "EMQX client installation failed"
      exit 1
    fi
  fi

  echo "EMQX client EXIST"
}

# Display the PID of the Chrome process
echo "Chrome process ID: $offline_pid"

config_file="config.properties"

if [ ! -f "$config_file" ]; then
  echo "Error: $config_file not found"
  exit 1
fi
echo "$config_file EXIST"

read_config() {
  emqx_server_address=$(awk -F'=' '/emqx_server_address/ {print $2}' config.properties)
  mqtt_port=$(awk -F'=' '/mqtt_port/ {print $2}' config.properties)
  username=$(awk -F'=' '/username/ {print $2}' config.properties)
  password=$(awk -F'=' '/password/ {print $2}' config.properties)
}

validate_config() {
  read_config
  while ! [[ $emqx_server_address =~ ^([0-9]{1,3}\.){3}[0-9]{1,3}$ ]]; do
    read -p "Enter the EMQX server address (IP address only): " emqx_server_address
    read -p "Enter the MQTT port: " mqtt_port
    read -p "Enter the username: " username
    read -p "Enter the password: " password

    if [[ $emqx_server_address =~ ^([0-9]{1,3}\.){3}[0-9]{1,3}$ ]]; then
      cat <<EOF >config.properties
emqx_server_address=$emqx_server_address
mqtt_port=$mqtt_port
username=$username
password=$password
EOF
      break
    else
      echo "Invalid input. Please enter a valid EMQX server address and MQTT port."
    fi
  done

}

check_client_id() {
  echo "check_client_id"
  if [ -s "$config_file" ] && grep -q "UNIQUE_KEY" "$config_file"; then
    unique_key=$(awk -F "=" '/UNIQUE_KEY/ {print $2}' "$config_file")
    if [ -n "$unique_key" ]; then
      echo "UNIQUE_KEY exists and is not empty $unique_key"
      return 0
    fi
  fi
  return 1
}

generate_and_save_unique_key() {
  echo "UNIQUE_KEY not exists"
  unique_key=$(uuidgen)
  echo "UNIQUE_KEY=$unique_key" >>"$config_file"
  echo "UNIQUE_KEY Generated $unique_key"
}

publish_key() {
  local topic="$1"
  local message="$2"
  output=$(mqtt pub -h "$emqx_server_address" -p "$mqtt_port" -t "$topic" -m "$message" 2>&1)
  if [[ $output =~ "Error: connect ECONNREFUSED" ]]; then
    # Error detected
    echo "Error connecting to MQTT broker: ECONNREFUSED"
    # Handle the error gracefully
    return 1 # Or perform other actions as needed
  else
    echo "SUCC"
    return 0
  fi
}

handle_received_message() {
    local received_message="$1"
      echo "Received message: $received_message"
}

subscribe_commands() {
  # Connect to MQTT broker
  if ! mqtt sub -h "$emqx_server_address" -p "$mqtt_port" -t "salam"; then
    echo "Connection error: ECONNREFUSED. Retrying..."
    play_content  # Optional: Trigger notification/action for connection error
    sleep 5  # Wait before retrying
    return 1  # Indicate error for potential retry logic
  fi

  # Subscribe to topic and handle messages
  while read -r message; do
    handle_received_message "$message"
  done < <(mqtt pub -h "$emqx_server_address" -p "$mqtt_port" -t "salam")

  # Close connection (optional)
  # mqtt unsub -h "$emqx_server_address" -p "$mqtt_port" -t "salam"
}

get_client_id() {
  local unique_key

  source "$config_file"

  if [[ -n "${UNIQUE_KEY}" ]]; then
    echo "$UNIQUE_KEY"
  else
    unique_key=$(uuidgen)
    echo "$unique_key"
    echo "UNIQUE_KEY=$unique_key" >>"$config_file"
  fi
  echo "$unique_key"
}
play_content() {
  while true; do
    publish_key "register_bash" "$unique_key" 
    status=$?
    if [ $status -eq 0 ]; then
      kill "$offline_pid"
      echo "Publish successful"
      subscribe_commands "$topic"
    else
      if ! kill -0 "$offline_pid" 2>/dev/null; then
        firefox "index.html?server=$emqx_server_address&port=$mqtt_port&uid=$unique_key" &
        offline_pid=$!
      fi
    fi
    sleep 5 # Add a delay before retrying
  done
}
main() {

  if ! validate_config; then

    error "Invalid configuration"
    return 1
  fi

  unique_key=$(get_client_id)
  if [[ -z $unique_key ]]; then

    unique_key=$(generate_unique_key)
    save_unique_key $unique_key
  fi
  play_content

}
main
