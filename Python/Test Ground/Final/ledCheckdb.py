
from gpiozero import LED


from firebase import firebase
led1 = LED(17)
print("Works!")

firebase = firebase.FirebaseApplication("https://fir-python-e6b67-default-rtdb.firebaseio.com/",None)

while True:    
    result = firebase.get("/fir-python-e6b67-default-rtdb/DeviceList/-Md151bARqLmD6XMC3-5",'')

    if(result["device_1"]==True):
        led1.on()
    else:
        led1.off()    
