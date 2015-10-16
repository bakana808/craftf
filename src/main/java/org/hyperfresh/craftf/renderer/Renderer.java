package org.hyperfresh.craftf.renderer;

import org.hyperfresh.craftf.Element;

/**
 * @author octopod
 *
 * A writer interface that converts a TextElement to an object type.
 */
public interface Renderer<T> {

	T render(Element element);
}
