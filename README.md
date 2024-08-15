# StringTemplateFormatter

`StringTemplateFormatter` is a Java utility class that allows you to create and format custom templates with named
placeholders. The placeholders can be replaced in any order, making it flexible for dynamic string construction.

## Features

* **Customizable Placeholders**: Placeholders are identified by a string key surrounded by a prefix and suffix (default:
  `${` and `}`).
* **Flexible Formatting**: Replace placeholders in any order, with the ability to preserve unmapped placeholders.
* **Performance**: Formatting is very fast and takes roughly the same as a `String.format` call, it's much better
  performance than using `MessageFormat.format`, and it's easier and more flexible for common usage.

## Usage

### Basic Example

```java
String example() {
	StringTemplateFormatter formatter = new StringTemplateFormatter();
	formatter.put("fruit", "Apple");
	formatter.put("name", "John");
	return formatter.format("Example: ${fruit}, ${number}, ${name}"); 
	// Output: "Example: Apple, ${number}, John"
}
```

### Custom Prefix and Suffix

You can customize the prefix and suffix used to identify placeholders:

```java
String example() {
	StringTemplateFormatter formatter = new StringTemplateFormatter("<%", "%>");
	formatter.put("item", "Book");
	return formatter.format("Buy a <%item%> today!"); 
	// Output: "Buy a Book today!"
}
```

---