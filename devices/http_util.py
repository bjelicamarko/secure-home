import requests
import json


def login(username, password):
    print(50 * "-" + "   LOGIN   " + 50 * "-")

    res = requests.post(
        "http://localhost:8081/api/users/login",
        json={"username": username, "password": password},
    )

    if res.status_code == 200:
        print("SCRIPT SUCCESSFULLY LOGGED IN\n")

    res_text = json.loads(str(res.text))
    token = res_text["accessToken"]
    cookie = res.headers["Set-Cookie"]

    return (res.status_code, token, cookie)


def post_data(token, cookie, request_body):
    print(50 * "-" + "   POST_DATA   " + 50 * "-" + "\n")
    res = requests.post(
        "http://localhost:8081/api/devices",
        headers={
            "Authorization": "Bearer " + token,
            "Cookie": cookie,
        },
        json=request_body,
    )

    return res.status_code


def post_data_list(token, cookie, request_body):
    print(50 * "-" + "   POST_DATA   " + 50 * "-" + "\n")
    res = requests.post(
        "http://localhost:8081/api/devices/all",
        headers={
            "Authorization": "Bearer " + token,
            "Cookie": cookie,
        },
        json=request_body,
    )

    return res.status_code
