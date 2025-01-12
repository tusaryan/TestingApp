package com.tusaryan.TestingApp;

import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.DisplayName;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
//import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
class TestingAppApplicationTests {

	@BeforeEach
	void setUp() {
		log.info("Starting the method, setting up the config");
	}

	@AfterEach
	void tearDown() {
		log.info("Tearing down the method");
	}

	@BeforeAll
    static void setUpOnce() {
		log.info("Setup Once...");
	}

	@AfterAll
	static void tearDownOnce() {
		log.info("Tearing down all...");
	}

	@Test
//	@Disabled
	void testNumberOne() {
		int a = 5;
		int b = 3;

		int result = addTwoNumbers(a, b);

		//import from org.junit.jupiter.api.Assertions
		//later we'll be using assertJ library which is a better way to do assertions
//		Assertions.assertEquals(8, result);

		//import from org.assertj.core.api.Assertions
//		Assertions.assertThat(result)
		//Alternative, since assertThat is a static method so import it statically
		//Eg: for Int
		assertThat(result)
				.isEqualTo(8)
				.isCloseTo( 9, Offset.offset(1));

		//Eg: for String
		//using .asLong() to convert the string to long; similar for Int, Float, Byte, etc.
		assertThat("Apple")
			.isEqualTo("Apple")
				.startsWith("App")
				.endsWith("le")
				.hasSize(5);

	}

	//import this from org.junit.jupiter.api.Test
	@Test
	//to change the display name in console
//	@DisplayName("displayTestNameTwo")
	//try to be as descriptive as you can while naming the test methods
	//testMethodName_whenCondition_thenResult
	void testDivideTwoNumbers_whenDenominatorIsZero_ThenArithmeticException() {
		int a =5;
		int b = 0;

		//import it statically from org.assertj.core.api.AssertionsForClassTypes
		assertThatThrownBy(() -> divideTwoNumbers(a, b))
				.isInstanceOf(ArithmeticException.class)
				//to check what the message I am getting here in the exception, is
				.hasMessage("Tried to divide by zero");
	}

	int addTwoNumbers(int a, int b) {
		return a + b;
	}

	double divideTwoNumbers(int a, int b) {
		try {
			//if we cast it to double then 5/0 will give result = Infinity;
			// so to get the exception we need to keep it as int
//			return (double) a / b;
			return a / b;
		} catch (ArithmeticException e) {
			log.error("Arithmetic Exception occured: " + e.getLocalizedMessage());
			throw new ArithmeticException("Tried to divide by zero");
		}
	}

}
