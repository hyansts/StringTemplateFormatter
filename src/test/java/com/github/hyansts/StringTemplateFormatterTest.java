package com.github.hyansts;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringTemplateFormatterTest {

	@Test
	public void testPartialFormating() {

		final String template = "Fruits: ${0}, ${1}, ${2}, ${3}, ${4}";

		StringTemplateFormatter formatter = new StringTemplateFormatter();
		formatter.put("0", "Apple");
		formatter.put("2", "Orange");

		String result = formatter.format(template);

		String expected = "Fruits: Apple, ${1}, Orange, ${3}, ${4}";
		assertEquals(expected, result);

		formatter.clear();

		formatter.put("1", "Banana");
		formatter.put("3", "Grape");

		String result2 = formatter.format(result);

		String expected2 = "Fruits: Apple, Banana, Orange, Grape, ${4}";
		assertEquals(expected2, result2);
	}

	@Test
	public void testFormatingValueWithPattern() {

		final String template = "Text: [${text}] Number: [${number}] Text again: [${text}]";

		StringTemplateFormatter formatter = new StringTemplateFormatter();
		formatter.put("text", "'${text}' is a placeholder.");
		formatter.put("number", "42");

		String result = formatter.format(template);
		String expected = "Text: ['${text}' is a placeholder.] Number: [42] Text again: ['${text}' is a placeholder.]";
		assertEquals(expected, result);
	}

	@Test
	public void testFormatingWithDifferentPattern() {

		final String TEMPLATE = "New pattern: &|0| Text: &|1| Default Pattern: ${2}";

		StringTemplateFormatter formatter = new StringTemplateFormatter("&|", "|");
		formatter.put("0", "&|key|");
		formatter.put("1", "This replaced a placeholder.");
		formatter.put("2", "This should not replace a placeholder.");

		String result = formatter.format(TEMPLATE);

		assertEquals("New pattern: &|key| Text: This replaced a placeholder. Default Pattern: ${2}", result);
	}

	@Test
	public void testFormatingWithLongerSuffix() {

		final String TEMPLATE = "New pattern: <%0%>! Text: <%1%>! Default Pattern: ${2}";

		StringTemplateFormatter formatter = new StringTemplateFormatter("<%", "%>!");
		formatter.put("0", "<%key%>!");
		formatter.put("1", "This replaced a placeholder.");
		formatter.put("2", "This should not replace a placeholder.");

		String result = formatter.format(TEMPLATE);

		assertEquals("New pattern: <%key%>! Text: This replaced a placeholder. Default Pattern: ${2}", result);
	}
}