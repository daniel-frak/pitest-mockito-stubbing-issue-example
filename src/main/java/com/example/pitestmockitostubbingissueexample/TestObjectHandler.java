package com.example.pitestmockitostubbingissueexample;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TestObjectHandler {

    private final Condition1Checker condition1Checker;
    private final Condition2Checker condition2Checker;
    private final TestEventPublisher eventPublisher;

    public void handle(List<TestObject1> testObject1List) {
        List<TestObject2> testObject2List = getTestObject2List(testObject1List);

        for (TestObject2 testObject2 : testObject2List) {
            if (conditionOneNotMet(testObject2) || conditionTwoNotMet(testObject2)) {
                continue;
            }

            publishEvent(testObject2);
        }
    }

    private List<TestObject2> getTestObject2List(
            List<TestObject1> testObject1List) {
        return testObject1List.stream()
                .map(this::toTestObject1)
                .distinct()
                .toList();
    }

    private TestObject2 toTestObject1(
            TestObject1 testObject1) {
        return new TestObject2(testObject1.value());
    }

    private void publishEvent(TestObject2 testObject2) {
        eventPublisher.publish(testObject2);
    }

    private boolean conditionOneNotMet(TestObject2 testObject2) {
        return !condition1Checker.checkCondition1(testObject2);
    }

    private boolean conditionTwoNotMet(TestObject2 testObject2) {
        return !condition2Checker.checkCondition2(testObject2);
    }
}
