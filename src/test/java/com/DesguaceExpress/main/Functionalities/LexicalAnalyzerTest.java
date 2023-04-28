package com.DesguaceExpress.main.Functionalities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LexicalAnalyzerTest {

    LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer();

    @Test
    void validateRegularExpression() {
        lexicalAnalyzer.validateRegularExpression("oas@gma.cs","email");



    }
}