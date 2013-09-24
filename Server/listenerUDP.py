import socket
import Xlib.display
import json


UDP_IP = "192.168.1.133"
UDP_PORT = 5005

CURSOR_X = 200
CURSOR_Y = 200

display = Xlib.display.Display()
screen = display.screen()
#INIT at 200,200
screen.root.warp_pointer(CURSOR_X,CURSOR_Y)
display.sync()

    
sock = socket.socket(socket.AF_INET,socket.SOCK_DGRAM)
sock.bind((UDP_IP,UDP_PORT))

def sync_display():
    global CURSOR_X, CURSOR_Y
    print(CURSOR_X,CURSOR_Y)
    screen.root.warp_pointer(CURSOR_X,CURSOR_Y)
    display.sync()

def move_pointer(gesture):
    global CURSOR_X, CURSOR_Y
    CURSOR_X += gesture['movement'][0]
    CURSOR_Y += gesture['movement'][1]
    sync_display()

def click_single(gesture):
    Xlib.ext.xtest.fake_input(display,Xlib.X.ButtonPress, 1)
    Xlib.ext.xtest.fake_input(display,Xlib.X.ButtonRelease, 1)
    sync_display()

def click_right(gesture):
    Xlib.ext.xtest.fake_input(display,Xlib.X.ButtonPress, 3)
    Xlib.ext.xtest.fake_input(display,Xlib.X.ButtonRelease, 3)
    sync_display()

def click_double(gesture):
    Xlib.ext.xtest.fake_input(display,Xlib.X.ButtonPress, 1)
    Xlib.ext.xtest.fake_input(display,Xlib.X.ButtonRelease, 1)
    sync_display()
    Xlib.ext.xtest.fake_input(display,Xlib.X.ButtonPress, 1)
    Xlib.ext.xtest.fake_input(display,Xlib.X.ButtonRelease, 1)
    sync_display()

def move_scrollwheel(gesture):
    if(gesture['movement'][1] < 0):
        Xlib.ext.xtest.fake_input(display,Xlib.X.ButtonPress, 4)
        Xlib.ext.xtest.fake_input(display,Xlib.X.ButtonRelease, 4)
        sync_display()
    else:
        Xlib.ext.xtest.fake_input(display,Xlib.X.ButtonPress, 5)
        Xlib.ext.xtest.fake_input(display,Xlib.X.ButtonRelease, 5)
        sync_display()
    
def move_workspace(gesture):
    if(gesture['movement'][1] < 0):
        Xlib.ext.xtest.fake_input(display, Xlib.X.KeyPress,37)
        Xlib.ext.xtest.fake_input(display, Xlib.X.KeyPress,64)
        Xlib.ext.xtest.fake_input(display, Xlib.X.KeyPress,116)
        Xlib.ext.xtest.fake_input(display, Xlib.X.KeyRelease,116)
        Xlib.ext.xtest.fake_input(display, Xlib.X.KeyRelease,64)
        Xlib.ext.xtest.fake_input(display, Xlib.X.KeyRelease,37)
    else:
        Xlib.ext.xtest.fake_input(display, Xlib.X.KeyPress,37)
        Xlib.ext.xtest.fake_input(display, Xlib.X.KeyPress,64)
        Xlib.ext.xtest.fake_input(display, Xlib.X.KeyPress,111)
        Xlib.ext.xtest.fake_input(display, Xlib.X.KeyRelease,111)
        Xlib.ext.xtest.fake_input(display, Xlib.X.KeyRelease,64)
        Xlib.ext.xtest.fake_input(display, Xlib.X.KeyRelease,37)
    sync_display()
        
        
    
 

while True:
    data, addr = sock.recvfrom(1024)
    print(data)
    gesture = json.loads(data)
    if(gesture['action'] == 'SCROLL' and gesture['pointer_count']==1):
        move_pointer(gesture)
    elif(gesture['action'] == 'SCROLL' and gesture['pointer_count']==2):
        move_scrollwheel(gesture)
    elif(gesture['action'] == 'SCROLL' and gesture['pointer_count']==3):
        move_workspace(gesture)
    elif(gesture['action'] == 'CLICK_SINGLE'):
        click_single(gesture)
    elif(gesture['action'] == 'CLICK_DOUBLE'):
        click_double(gesture)
    elif(gesture['action'] == 'CLICK_RIGHT'):
        click_right(gesture)




