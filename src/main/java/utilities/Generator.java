package utilities;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import com.github.javafaker.Faker;

public class Generator {
	
	private static Faker fake = new Faker(new Locale("ru"));
	
	
	public static String getCurrentDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyy");
		LocalDate localDate = LocalDate.now();
		String currentDate = dtf.format(localDate);
		return currentDate;

	};

	public static String getCurrentDatePlus5() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyy");
		LocalDate localDate = LocalDate.now().plusDays(5);
		String currentDatePlus5 = dtf.format(localDate);
		return currentDatePlus5;

	};

	public static String getDocumentNumber() {

		int documentNumber = (int) Math.floor(Math.random() * 10000);
		String documentNumberString = Integer.toString(documentNumber);

		return documentNumberString;

	};

	public static int getRandomUpTo(int multiplier) {

		int randomTen = (int) Math.floor(Math.random() * multiplier);
		return randomTen;

	};

	public static String randomCAOdistrict() {
		String[] districts = { "Арбат", "Басманный", "Замоскворечье", "Красносельский", "Мещанский", "Пресненский",
				"Таганский", "Тверской", "Хамовники", "Якиманка" };
		String district = districts[(int) Math.floor(Math.random() * districts.length)];
		return district;
	}

	public static String randomInspTime() {
		String hours = Integer.toString(getRandomUpTo(8) + 10);
		String[] mins = { "00", "15", "30", "45" };
		String time = hours + ":" + mins[getRandomUpTo(3)];
		return time;
	}
	
	public static String fakeKadastr() {
		return fake.bothify("41:##:#######:##");
	}

	public static String fakeContractNum() {
		return fake.bothify("??-####-##");
	}
	
	public static String fakeInn() {
		return Long.toString(fake.number().randomNumber(12, true));
	}
	
	public static String fakeDatePast() {
		return new SimpleDateFormat("dd-MM-yyyy").format(fake.date().past(5000, TimeUnit.DAYS));
	}
	public static String fakeDateFuture() {
		return new SimpleDateFormat("dd-MM-yyyy").format(fake.date().future(5000, TimeUnit.DAYS));
	}
}
