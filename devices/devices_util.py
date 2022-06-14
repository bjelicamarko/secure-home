import time
import random

from crypto_util import *

dictionary = {
    "dict_air_conditioner": {
        "REGULAR": [
            "Temperature is optimal",
            "Temperature is too low",
        ],
        "PANIC": ["Temperature is too high."],
    },
    "dict_fridge": {
        "REGULAR": [
            "Temperature is optimal",
            "Temperature isnt optimal",
        ],
        "PANIC": ["Temperature in fride is too low, please check thermometer."],
    },
    "dict_door": {
        "REGULAR": [
            "Opened",
            "Closed",
            "Locked",
        ],
        "PANIC": ["The door is open by force, please check "],
    },
    "dict_smoke_detector": {
        "REGULAR": ["No smoke detected", "Detected small quantity of smoke"],
        "PANIC": ["Smoke concentration is too high, please check the kitchen."],
    },
    "dict_cooker": {
        "REGULAR": ["Stove is on", "Stove has low temperature"],
        "PANIC": ["Stove is burning out, temperature is too high, please turn device off."],
    },
    "dict_water_heater": {
        "REGULAR": ["Temprature of water is 22C", "Temprature of water is 32C"],
        "PANIC": ["Water temperature is too high, please turn device off."],
    },
}


def generate_messages_from_devices():
    message_list = []
    message_list.append(generate_message("Air conditioner", "dict_air_conditioner", "REGULAR"))
    message_list.append(generate_message("Fridge", "dict_fridge", "REGULAR"))
    message_list.append(generate_message("Front door", "dict_door", "REGULAR"))
    message_list.append(generate_message("Backyard door", "dict_door", "REGULAR"))
    message_list.append(generate_message("Smoke detector", "dict_smoke_detector", "REGULAR"))
    message_list.append(generate_message("Cooker", "dict_cooker", "REGULAR"))
    message_list.append(generate_message("Water heater", "dict_water_heater", "REGULAR"))

    print(message_list)

    for message in message_list:
        message["message"] = encrypt(message["message"])

    print(message_list)

    return message_list

def generate_panic_messages_from_devices():
    message_list = []
    message_list.append(generate_message("Air conditioner", "dict_air_conditioner", "PANIC"))
    message_list.append(generate_message("Fridge", "dict_fridge", "PANIC"))
    message_list.append(generate_message("Front door", "dict_door", "PANIC"))
    message_list.append(generate_message("Backyard door", "dict_door", "PANIC"))
    message_list.append(generate_message("Smoke detector", "dict_smoke_detector", "PANIC"))
    message_list.append(generate_message("Cooker", "dict_cooker", "PANIC"))
    message_list.append(generate_message("Water heater", "dict_water_heater", "PANIC"))

    print(message_list)

    for message in message_list:
        message["message"] = encrypt(message["message"])

    print(message_list)

    return message_list


def generate_message(device_name, dict_name, message_status):

    # regular message
    if message_status == "REGULAR":
        index = random.randint(0, len(dictionary[dict_name][message_status]) - 1)

        return {
            "deviceName": device_name,
            "timestamp": int(round(time.time() * 1000)),
            "messageStatus": message_status,
            "message": dictionary[dict_name][message_status][index],
        }
    
    # panic message
    return {
            "deviceName": device_name,
            "timestamp": int(round(time.time() * 1000)),
            "messageStatus": message_status,
            "message": dictionary[dict_name][message_status][0],
        }
