package com.ll.multi_crawler.ml.serivce;

import jakarta.annotation.PostConstruct;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Arrays;

@Service
public class OpenNlpNerService {

    @Value("classpath:ml/opennlp-ko-ud-kaist-tokens-1.3-2.5.4.bin")
    private Resource tokenizerModelResource;

    @Value("classpath:ml/en-ner-person.bin")
    private Resource nerModelResource;

    private TokenizerME tokenizer;
    private NameFinderME nameFinder;

    @PostConstruct
    public void init() throws Exception {
        try (InputStream tokenizerStream = tokenizerModelResource.getInputStream();
             InputStream nerStream = nerModelResource.getInputStream()) {

            TokenizerModel tokenizerModel = new TokenizerModel(tokenizerStream);
            tokenizer = new TokenizerME(tokenizerModel);

            TokenNameFinderModel nerModel = new TokenNameFinderModel(nerStream);
            nameFinder = new NameFinderME(nerModel);
        }
    }

    public String[] findEntities(String text) {
        System.out.println("text = " + text);
        String[] tokens = tokenizer.tokenize(text);
        System.out.println("tokens = " + Arrays.toString(tokens));
        Span[] spans = nameFinder.find(tokens);
        System.out.println("spans = " + Arrays.toString(spans));

        return Arrays.stream(spans)
                .map(s -> String.join(" ", Arrays.copyOfRange(tokens, s.getStart(), s.getEnd())))
                .toArray(String[]::new);
    }
}
