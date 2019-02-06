<p align="center">
  <img src="ble-java-logo.png" alt="ble-java logo"/>
</p>

**ble-java** is a java library for building **BLE** GATT peripherals role application in JAVA.

**ble-java** is based on BlueZ, the linux Bluetooth stack.

# Features
* Create GATT Services
* Create GATT Characteristic
* Customize the Peripheral name
* Pure JAVA library

# Dependencies
1. Java 8 or better
2. BlueZ 5.43 or better
3. d-bus Java library `libdbus-java`
Raspbian pl.edu.wat.szarza:
```
sudo apt-get install
```
you may also have to do run
```
sudo ldconfig
```

# Install
Clone the repository and build with Gradle (4.5 or higher):
```
gradle build
```

# Application
You could see the SIOBleaApplication `SIOBleApplication.java` in `src/test/java/pl.edu.wat.szarza`.
It's a application that create a BLE Application with one Service and 1 Characteristic.

## Build
```
./gradlew jar
```

## Run on RPi
```
sudo java -jar build/libs/ble-java-0.1.jar
```

# BlueZ compatibility
Until now is tested with BlueZ 5.46 on Raspbian distribution.

ble-java use the GattManager and LEAdvertising that was marked "Experimental" since 5.47, so you have to enable the Experimental features with the `--experimental` parameter in the BlueZ startup service.

Example for Raspbian `/lib/systemd/system/bluetooth.service`

```
...
bluetoohd --experimental
...
```

In the BlueZ 5.48 seem to be removed the experimental tag on the LEAdvertising features, but it was not yet tried.

For more info about BlueZ see [http://www.bluez.org](http://www.bluez.org).

# Help
If you need help you can write me an email or you can open a new issue.
