Feature: All links ok?
    Check pages contain no broken links

    Scenario: Check W3 bad page has no broken links
        Given I opened Chrome
        When I am on the W3 bad page
        Then Page has no broken links

    Scenario: Check W3 multimodal page has no broken links
        Given I opened Chrome
        When I am on the W3 multimodal page
        Then Page has no broken links

    Scenario: Check W3 htmlcss page has no broken links
        Given I opened Chrome
        When I am on the W3 htmlcss page
        Then Page has no broken links
    
    Scenario: Check W3 bad page has no broken links
        Given I opened Firefox
        When I am on the W3 bad page
        Then Page has no broken links

    Scenario: Check W3 multimodal page has no broken links
        Given I opened Firefox
        When I am on the W3 multimodal page
        Then Page has no broken links

    Scenario: Check W3 htmlcss page has no broken links
        Given I opened Firefox
        When I am on the W3 htmlcss page
        Then Page has no broken links