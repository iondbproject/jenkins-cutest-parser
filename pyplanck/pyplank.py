#!python3

import serial
import subprocess as sp
import shutil
import os
import sys

#Wrapper around os.call. Checks return code and fast-fails if we're not OK.
def syscall(cmd):
    if sp.call(cmd.split()) != 0:
        sys.exit(cmd + " failed with abnormal exit code")

def main():
    ###
    #Config
    ###
    build_folder = "iondev/"

    #From cmd args, get the port and target test
    port = sys.argv[1]
    targ_test = sys.argv[2]

    #Cleanup old tests (if exist)
    try:
        shutil.rmtree(build_folder)
    except FileNotFoundError:
        print(build_folder, "folder was not found, not removed...")

    #Create, and change directory to build folder
    os.mkdir(build_folder)
    os.chdir(build_folder)

    #Call ino init, and remove the sketch file it created
    syscall("ino init")
    os.remove("src/sketch.ino")

    #### TODO
    #Here, we should copy in all the iondb artifacts.
    #### TODO

    #Copy over the requested test into the source dir
    shutil.copy(targ_test, "src/test.ino")

    #Build, and upload the sketch
    syscall("ino build")
    syscall("ino upload")

    #Pop back to original directory
    os.chdir("../")

    #Init serial connection
    ser = serial.Serial(port, timeout=10)

    #Read the connection until there's nothing left to read.
    #####TODO
    #Here we should do the watchdog check and fail if it we read for too long.
    #####TODO
    charin = ser.read().decode("ascii")
    while charin != "":
        print(charin, end="")
        charin = ser.read().decode("ascii")

if __name__ == '__main__':
    main()