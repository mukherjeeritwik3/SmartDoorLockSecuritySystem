import pywhatkit as pwt
import pyautogui as pg
import random
import time
import os

from firebase import firebase
firebase = firebase.FirebaseApplication("https://fir-python-e6b67-default-rtdb.firebaseio.com/",None)

while True:
    result=firebase.get("fir-python-e6b67-default-rtdb/SecuritySystem/-Mn9auOPpOiMosS31Wpw",'')
    otpValue = result["otp"]

    if(otpValue==True):
        phone_num= str(result["phone"])
        sixDigitRandom = random.randrange(10000,99999)
        pwt.sendwhatmsg_instantly(phone_num,str(sixDigitRandom),5)
        time.sleep(5)
        pg.press("tab")
        pg.press("tab")
        pg.press("tab")
        pg.press("tab")
        pg.press("tab")
        pg.press("tab")
        pg.press("tab")
        pg.press("tab")
        pg.press("tab")
        pg.press("enter")
        time.sleep(3)
        os.system("taskkill/im chrome.exe")
        time.sleep(2)
        firebase.put("fir-python-e6b67-default-rtdb/SecuritySystem/-Mn9auOPpOiMosS31Wpw","otp",sixDigitRandom)
        print("success")
        time.sleep(3)
    else:
        time.sleep(3)
        
        print("None")