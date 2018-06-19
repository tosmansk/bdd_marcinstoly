Feature: marcinstolypagetest
  This will test values and results returned by tosmansk.pythonanywhere.com/marcinstoly

  Scenario: Verify REST response data
    Given Log into page marcinstoly
    When Provide table data
      | Stol   | dl  | sz  |
      | Henryk | 180 | 110 |
      | Henryk | 200 | 150 |
      | Denis  | 180 | 110 |
      | Denis  | 200 | 160 |
    Then Result page should be sent


  Scenario Outline: Verify table calculations
    Given Log into page marcinstoly
    When Provide table with "<stol>" with length "<dl>" and width "<sz>"
    Then Result page should contain "<stol>" "<dl>" "<sz>" "<rg_rw>" "<rg_rp>" "<oskwew_rw>" "<oskwew_rp>" "<oskzew_rw>" "<oskzew_rp>" "<mask_rw>", "<mask_rp>"

    #Zamienic na cyfle dl i sz
    Examples:
      | stol   | dl  | sz  | rg_rw | rg_rp | oskwew_rw | oskwew_rp | oskzew_rw | oskzew_rp | mask_rw | mask_rp |
      | Henryk | 180 | 110 | 180.2 | 90.0  | 162.1     | 90.0      | 151.2     | 81.2      | 174.4   | 98.4    |
      | Henryk | 200 | 150 | 200.2 | 130.0 | 182.1     | 130.0     | 171.2     | 121.2     | 194.4   | 138.4   |
      | Denis  | 180 | 110 | 182.2 | 94.0  | 166.1     | 94.0      | 164.8     | 94.8      | -       | -       |
      | Denis  | 200 | 160 | 200.2 | 144.0 | 186.1     | 144.0     | 184.8     | 144.8     | -       | -       |