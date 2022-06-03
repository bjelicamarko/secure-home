from schedule import every, run_pending
import time

from devices_util import *
from http_util import *


def main():

    status_code, token, cookie = login("panicdev", "nisamadmin")

    if status_code != 200:
        print("QUIT")
        return

    def job():

        # for now there is only regular messages
        request_body = generate_panic_messages_from_devices()

        status = post_data_list(token, cookie, request_body)
        print("Server responded with status " + str(status))

    every(10).seconds.do(job)

    while True:
        run_pending()
        time.sleep(1)


if __name__ == "__main__":
    main()
