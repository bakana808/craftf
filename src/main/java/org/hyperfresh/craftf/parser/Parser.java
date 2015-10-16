package org.hyperfresh.craftf.parser;

import org.hyperfresh.craftf.Element;

/**
 * @author octopod
 *
 * A parser interface that reads an object of type {@code T} and creates a TextElement.
 */
public interface Parser<T>
{
	/**
	 * Parses an object of type {@code T}, and returns a TextElement.
	 *
	 * @param input the source object
	 * @return a CFText instance.
	 */
	Element parse(T input);
}
