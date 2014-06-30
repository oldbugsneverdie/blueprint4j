package com.blueprint4j.core.translate;

import java.io.IOException;

/**
 * Interface that indicates that this object is translatable.
 *
 */
public interface Translatable {

	public void accept(Translator translator);


}
