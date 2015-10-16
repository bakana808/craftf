package org.hyperfresh.craftf;

import org.hyperfresh.craftf.enums.ChatColor;
import org.junit.Assert;
import org.junit.Test;

public class JSONRendererTest {

	public void testParser(String src, Element el) {
		Assert.assertEquals(CraftF.render(el), CraftF.evaluate(src));
	}

	@Test
	public void textTest() {
		testParser("test", new ChatElement("test"));
	}

	@Test
	public void newlineTest() {
		testParser("test\ntest", new ChatElement("test\ntest"));
	}

	@Test
	public void colorTest() {
		testParser("&agreen", new ChatElement("green").color(ChatColor.GREEN));
	}

	@Test
	public void multicolorTest() {
		testParser("&agreen&cred", new ChatElement("green", ChatColor.GREEN).attach(new ChatElement("red", ChatColor.RED)));
	}

	@Test
	public void resetTest() {
		testParser("&agreen&rwhite", new ChatElement("green", ChatColor.GREEN).attach(new ChatElement("white")));
	}

	//@Test
	//public void colorTest2() {
	//
	//	String source = "&atest&ctest";
	//	Assert.assertEquals("{\"text\":\"test\",\"color\":\"green\"}", Craft.F(source));
	//}
}
