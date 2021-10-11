Feature: Response code?
    Check no errors are shown in console

    Scenario: Check W3 bad page loading raises no errors in console
        Given I opened Chrome
        When I am on the W3 bad page
        Then The console log has no errors

    Scenario: Check W3 multimodal page loading raises no errors in console
        Given I opened Chrome
        When I am on the W3 multimodal page
        Then The console log has no errors

    Scenario: Check W3 htmlcss page loading raises no errors in console
        Given I opened Chrome
        When I am on the W3 htmlcss page
        Then The console log has no errors
    
    Scenario: Check W3 bad page loading raises no errors in console
        Given I opened Firefox
        When I am on the W3 bad page
        Then The console log has no errors

    Scenario: Check W3 multimodal page loading raises no errors in console
        Given I opened Firefox
        When I am on the W3 multimodal page
        Then The console log has no errors

    Scenario: Check W3 htmlcss page loading raises no errors in console
        Given I opened Firefox
        When I am on the W3 htmlcss page
        Then The console log has no errors