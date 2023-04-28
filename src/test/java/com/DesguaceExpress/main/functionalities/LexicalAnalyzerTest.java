package com.DesguaceExpress.main.functionalities;

import org.junit.jupiter.api.Test;

class LexicalAnalyzerTest {

    LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer();

    @Test
    void validateRegularExpression() {
        lexicalAnalyzer.validateRegularExpression("oas@gma.cs","email",null);



    }
}