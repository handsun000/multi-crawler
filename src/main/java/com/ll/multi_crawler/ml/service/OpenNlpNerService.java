package com.ll.multi_crawler.ml.service;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.Span;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

@Service
public class OpenNlpNerService {

    private final NameFinderME nameFinder;

    public OpenNlpNerService(String path) throws IOException {
        TokenNameFinderModel model = new TokenNameFinderModel(new FileInputStream(path));
        nameFinder = new NameFinderME(model);
    }

    public String[] findEntities(String text) {
        String[] tokens = WhitespaceTokenizer.INSTANCE.tokenize(text);
        Span[] spans = nameFinder.find(tokens);

        return Arrays.stream(spans)
                .map(s -> String.join(" ", Arrays.copyOfRange(tokens, s.getStart(), s.getEnd())))
                .toArray(String[]::new);
    }
}
