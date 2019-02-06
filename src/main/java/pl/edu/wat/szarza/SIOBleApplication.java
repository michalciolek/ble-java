package pl.edu.wat.szarza;

import it.tangodev.ble.*;
import it.tangodev.ble.BleCharacteristic.CharacteristicFlag;
import org.freedesktop.dbus.exceptions.DBusException;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SIOBleApplication {
	private String characteristicValue = "auth";
	private BleApplication app;
	private BleService service;
	private BleCharacteristic characteristic;

	public void notifyBle(String value) {
		this.characteristicValue = value;
		characteristic.sendNotification();
	}
	
	public SIOBleApplication() throws DBusException, InterruptedException {
		BleApplicationListener appListener = new BleApplicationListener() {
			@Override
			public void deviceDisconnected() {
				System.out.println("Device disconnected");
			}
			
			@Override
			public void deviceConnected() {
				System.out.println("Device connected");
			}
		};
		app = new BleApplication("/tango", appListener);
		service = new BleService("/tango/s", "13333333-3333-3333-3333-333333333001", true);
		List<CharacteristicFlag> flags = new ArrayList<CharacteristicFlag>();
		flags.add(CharacteristicFlag.READ);
		flags.add(CharacteristicFlag.WRITE);
		flags.add(CharacteristicFlag.NOTIFY);
		
		characteristic = new BleCharacteristic("/tango/s/c", service, flags, "13333333-3333-3333-3333-333333333002", new BleCharacteristicListener() {
			@Override
			public void setValue(byte[] value) {
				try {
					characteristicValue = new String(value, StandardCharsets.UTF_8);
				} catch(Exception e) {
					System.out.println("");
				}
			}
			
			@Override
			public byte[] getValue() {
				try {
					return characteristicValue.getBytes(StandardCharsets.UTF_8);
				} catch(Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
		service.addCharacteristic(characteristic);
		app.addService(service);

		app.start();
	}

	public BleApplication getApp() {
		return app;
	}

	public static void main(String[] args) throws DBusException, InterruptedException {
		SIOBleApplication SIOBleApplication = new SIOBleApplication();

		int i = 0;
		while (true) {
			Thread.sleep(6000);
			System.out.println("notify" +i);
			SIOBleApplication.notifyBle("auth "+i);
			i++;

			if (i > 9999999) {
				break;
			}
		}

		System.out.println("Stopping application");
		SIOBleApplication.getApp().stop();
		System.out.println("Application stopped");
	}
	
}
