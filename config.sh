#!/bin/bash
clientId="$1"
# Capture the parameters
ip_address=$(ip addr show | grep -o 'inet [0-9.]*' | awk '{print $2}' | head -1)
total_memory=$(free -m | awk 'NR==2{print $2}')
cpu_usage=$(top -bn1 | grep "Cpu(s)" | awk '{print $2 + $4}')
resolution=$(xrandr | awk '/\*/ {print $1}')
width=$(xdpyinfo | awk '/dimensions/ {print $2}' | cut -d 'x' -f1)
height=$(xdpyinfo | awk '/dimensions/ {print $2}' | cut -d 'x' -f2)
wifi_status=$(nmcli radio wifi)
bluetooth_status=$(rfkill list bluetooth | awk '/Soft blocked/ {print $3}')
lan_status=$(nmcli device show eth0 | awk '/GENERAL.DEVICE:/ {print $2}')
sound_volume=$(amixer get Master | awk -F '[][]' '/%/ {print $2}' | head -1)
brightness=$(cat /sys/class/backlight/*/brightness)
monitor_name=$(xrandr | awk '/ connected/ {print $1; exit}')
orientation=$(xrandr --query | awk '/ connected/{getline; print $4}')
mac_address=$(ifconfig | grep -Eo '([0-9A-Fa-f]{2}:){5}[0-9A-Fa-f]{2}')

json_object=$(cat << EOF
{
  "clientId":"$clientId",
  "ipAddress": "$ip_address",
  "totalMemory": "$total_memory",
  "cpuUsage": "$cpu_usage",
  "resolution": "$resolution",
  "width": "$width",
  "height": "$height",
  "wifiStatus": "$wifi_status",
  "bluetoothStatus": "$bluetooth_status",
  "lanStatus": "$lan_status",
  "soundVolume": "$sound_volume",
  "brightness": "$brightness",
  "monitorName": "$monitor_name",
  "orientation": "$orientation",
  "macAddress":"$mac_address"
}
EOF
)

# Export the JSON object as a variable
export json_config="$json_object"