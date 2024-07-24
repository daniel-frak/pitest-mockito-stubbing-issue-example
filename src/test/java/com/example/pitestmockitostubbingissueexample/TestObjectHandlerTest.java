package com.example.pitestmockitostubbingissueexample;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestObjectHandlerTest {

    @Mock
    private Condition1Checker condition1Checker;

    @Mock
    private Condition2Checker condition2Checker;

    @Mock
    private TestEventPublisher eventPublisher;

    private TestObjectHandler handler;

    @BeforeEach
    void setUp() {
        handler = new TestObjectHandler(condition1Checker, condition2Checker, eventPublisher);
    }

    @Test
    void shouldPublishEventGivenBothConditionsAreMet() {
        TestObject1 testObject1 = testObject1();
        TestObject2 testObject2 = testObject2();
        mockCondition1IsMet(testObject2);
        mockCondition2IsMet(testObject2);

        handler.handle(List.of(testObject1));

        verify(eventPublisher).publish(any());
    }

    private TestObject1 testObject1() {
        return new TestObject1("Test Object");
    }

    private TestObject2 testObject2() {
        return new TestObject2("Test Object");
    }

    private void mockCondition2IsMet(TestObject2 testObject2) {
        boolean isMet = true;
        mockCondition2(testObject2, isMet);
    }

    private void mockCondition2(TestObject2 testObject2, boolean isMet) {
        when(condition2Checker.checkCondition2(testObject2))
                .thenReturn(isMet);
    }

    private void mockCondition1IsMet(TestObject2 testObject2) {
        boolean isMet = true;
        mockCondition1(testObject2, isMet);
    }

    private void mockCondition1(TestObject2 testObject2, boolean doExist) {
        when(condition1Checker.checkCondition1(testObject2))
                .thenReturn(doExist);
    }

    @Test
    void shouldNotPublishEventGivenCondition1IsMetAndCondition2IsNotMet() {
        TestObject1 testObject1 = testObject1();
        TestObject2 testObject2 = testObject2();
        mockCondition1IsMet(testObject2);
        mockCondition2IsNotMet(testObject2);

        handler.handle(List.of(testObject1));

        verify(eventPublisher, never()).publish(any());
    }

    private void mockCondition2IsNotMet(TestObject2 testObject2) {
        boolean isMet = false;
        mockCondition2(testObject2, isMet);
    }
}