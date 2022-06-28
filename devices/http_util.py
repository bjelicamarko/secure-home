import requests
import json


def login(username, password):
    print(42 * "-" + "   LOGIN   " + 42 * "-")

    res = requests.post(
        "https://localhost:8081/api/users/login",
        json={"username": username, "password": password},
        verify=False
    )

    if res.status_code == 200:
        print("SCRIPT SUCCESSFULLY LOGGED IN\n")

    res_text = json.loads(str(res.text))
    token = res_text["accessToken"]
    cookie = res.headers["Set-Cookie"]

    return (res.status_code, token, cookie)


def post_data(token, cookie, request_body):
    print(40 * "-" + "   POST_DATA   " + 40 * "-" + "\n")
    res = requests.post(
        "https://localhost:8081/api/devices",
        headers={
            "Authorization": "Bearer " + token,
            "Cookie": cookie,
        },
        json=request_body,
        verify=False
    )

    return res.status_code


def post_data_list(token, cookie, request_body):
    print(40 * "-" + "   POST_DATA   " + 40 * "-" + "\n")
    res = requests.post(
        "https://localhost:8081/api/devices/all",
        headers={
            "Authorization": "Bearer " + token,
            "Cookie": cookie
        },
        json=request_body,
        verify=False
    )

    return res.status_code
