package com.blueprint4j.core.translate;

import java.util.ArrayList;
import java.util.List;

public class TranslatorList {

    private List<Translator> translators = new ArrayList<Translator>();

    public Translator add(Translator translator){
        translators.add(translator);
        return translator;
    }

    public List<Translator> getTranslators(){
        return translators;
    }
}
