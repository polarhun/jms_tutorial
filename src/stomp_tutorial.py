import stomp
import time


class MyListener(stomp.ConnectionListener):
    def on_error(self, headers, message):
        print('received an error "%s"' % message)

    def on_message(self, headers, message):
        print('received a message "%s"' % message)


conn = stomp.Connection([("127.0.0.1", 61613)])
conn.set_listener("", MyListener())
conn.start()
conn.connect(wait=True)
conn.subscribe(destination="/queue/TUTORIAL.PRESENTER", id=1, ack="auto")
conn.send(body="HELLO FROM PYTHON", destination="/queue/TUTORIAL.PRESENTER")
time.sleep(2)
conn.disconnect()
