
CraftF Specifications
===

- 1 Parser
	- 1.1 Rules
		- 1.1.1 Text
		- 1.1.2 Colors & Styles
		- 1.1.3 Events
	- 1.2 Custom Parsers
- 2 Builder
	- 2.1 Usage
- 3 Renderer
	- 3.1 Custom Renderers
- 4 Misc
	- 4.1 Filler Generator
	- 4.2 Pseudo-Tabs
	- 4.3 Custom Font Support

---

1 Parser
---

### 1.1 Rules

#### 1.1.1 Text


Any amount of text without any formatting will be output as one JSON object.

```
	this is a sentence
```

#### 1.1.2 Colors & Styles

Use legacy-style syntax `&<#>` color codes to apply a color to any text after it.

```
	&cred &agreen
```

#### 1.1.3 Events

```
	&{ cmd help }&[click me for help!]
```

### 1.2 Custom Parsers

2 Builder
---

### 2.1 Usage

#### Building a Chat Message

```java
ChatElement el = new ChatElement("craftf").color(ChatColor.GREEN).style(ChatStyle.BOLD);

/*
	output: "{"text":"craftf","color":"green","bold":"true"}"
*/
System.out.println(CraftF.render(el))

/*
	output: "§a§lcraftf"
*/
System.out.println(CraftF.render(el, CraftF.getLegacyRenderer()));
```

3 Renderer
---

### 3.1 Custom Renderers

4 Misc
---

### 4.2 Pseudo-Tabs

Because tabs aren't natively supported in Minecraft, we try to build around that by offering pseudo-tabs, which are created by using a combination of spaces and filler characters to achieve alignment in text.

You can test out the filler generator yourself by running `craftf().createFiller(40)`, which will create filler at a pixel width (40, for example).

### 4.3 Custom Font Support

Because of craftf's dependence on the character sizes of a font, we provide an interface `CFFontInfo` that will allow developers to support custom fonts.

```java
public class CustomFontInfo implements CFFontInfo
{
	@Override
	public int getWidth(char c)
	{
		//return width lookups here
	}
}
```

A vanilla implementation, `CFVanillaFontInfo`, is already provided.
