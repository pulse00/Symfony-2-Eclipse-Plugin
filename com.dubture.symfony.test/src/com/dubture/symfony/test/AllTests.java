package com.dubture.symfony.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.dubture.symfony.test.codeassist.DoctrineCodeAssistTest;
import com.dubture.symfony.test.codeassist.SymfonyCodeAssistTest;

@RunWith(Suite.class)
@SuiteClasses({ AnnotationCommentParserTest.class, AnnotationParserTest.class, IndexTest.class, ModelUtilsTest.class,
		PathUtilsTest.class, RoutingParserTest.class, TextSequenceUtilityTest.class, XMLParserTest.class,
		YamlTest.class, SymfonyCodeAssistTest.class, DoctrineCodeAssistTest.class })
public class AllTests {

}
