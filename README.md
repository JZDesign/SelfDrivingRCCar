# SelfDrivingRCCar
## A training controller for a Self Driving RC Car
### Hack MT 2019

This application originally lived inside of a [master repository](https://github.com/nibraaska/SelfDrivingRCCar) for team 21's self driving car project. Due to clutter and git confusion this was packaged and moved into its own repo.

## Technical notes

This project originally was being built in `Java`. A Joystick plugin was utilized to create the control. Connection to the Raspberry Pi was made via Websocket. In an effort to send less data to the Pi a timer implementation was laid out. Data is sent via websocket in strings representing boolean values : `1000`, `1100`, `1010`, etc., This data was grabbed by the pi to append to image data for training. 

### Migrating to Kotlin

Java's cumbersome nature and sometimes ambiguous syntax presented many questions. A decision was made to migrate the existing code to Kotlin in order to reduce the noise and confusion. Not only was the code footprint was cut by 75%, Kotlin enhanced our ability to write self documenting code.

### Bumps and Bruises

Aside from the haze of next to zero sleep, masked by caffeine and excitement, that assisted in much `Error Code: ID10T, Message: User Error` there were a few notable obstacles in the implementation of this project.

Threading was a consideration when implementing the timed sending of data through the socket that resulted in deep dives into the debugger and stackoverflow. 

When connecting to the Pi we discovered that sometimes the socket port was already occupied and was unable for connection, although the android and Pi had "disconnected." This was addressed by adding a UI interface to change ports.

*Growing Pains* were experienced during the migration from Java, but stretching out and enjoying the freedom of Kotlin greatly outweighed that discomfort.

### What worked, What didn't

Attempted with: | Success | Pending Success | Failure
--------------- | ------- | --------------- | ----------
Migrating to Kotlin from Java | X | |
Establishing a socket | X | |
Sending a stream of data via socket | X | |
Controlling the data via Joystick | X | |
Changing the port through the UI | X | |
Disconnecting from Socket | | X | 
Timing the data sent through the socket | | | X
Controlling torque and steering of RC target | X | |
Calibrating Pi to allow for accurate control | | X |

## Building this project
0. Establish a socket port to receive the data
1. Install your IDE of choice like Android Studio and get acquainted with it.
2. Clone or download this repo and run it on your device or emulator
3. Set the port and play with the joystick.
