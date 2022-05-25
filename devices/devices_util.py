import time
import random

dictionary = {
    "dict_air_conditioner": {
        "REGULAR": [
            "Temperature is optimal",
            "Temperature isnt optimal",
            "Temperature is too low",
        ],
        "WARNING": [],
        "PANIC": [],
    },
    "dict_fridge": {
        "REGULAR": [
            "Temperature is optimal",
            "Temperature isnt optimal",
            "Temperature is too low",
        ],
        "WARNING": [],
        "PANIC": [],
    },
    "dict_door": {
        "REGULAR": [
            "Opened",
            "Closed",
            "Locked",
        ],
        "WARNING": [],
        "PANIC": [],
    },
    "dict_smoke_detector": {
        "REGULAR": ["No smoke detected", "Detected small quantity of smoke"],
        "WARNING": [],
        "PANIC": [],
    },
    "dict_cooker": {
        "REGULAR": ["Stove is on", "Stove has low temperature"],
        "WARNING": [],
        "PANIC": [],
    },
    "dict_water_heater": {
        "REGULAR": ["Temprature of water is 22C", "Temprature of water is 32C"],
        "WARNING": [],
        "PANIC": [],
    },
}


def generate_messages_from_devices():
    message_list = []
    message_list.append(generate_message("Air conditioner", "dict_air_conditioner"))
    message_list.append(generate_message("Fridge", "dict_fridge"))
    message_list.append(generate_message("Front door", "dict_door"))
    message_list.append(generate_message("Backyard door", "dict_door"))
    message_list.append(generate_message("Smoke detector", "dict_smoke_detector"))
    message_list.append(generate_message("Cooker", "dict_cooker"))
    message_list.append(generate_message("Water heater", "dict_water_heater"))

    return message_list


def generate_message(device_name, dict_name):
    messageStatus = "REGULAR"

    index = random.randint(0, len(dictionary[dict_name][messageStatus]) - 1)

    return {
        "deviceName": device_name,
        "timestamp": int(round(time.time() * 1000)),
        "messageStatus": messageStatus,
        "message": dictionary[dict_name][messageStatus][index],
    }
