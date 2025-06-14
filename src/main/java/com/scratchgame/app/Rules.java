package com.scratchgame.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

public class Rules {
    @JsonProperty("columns")
    private int columns;

    @JsonProperty("rows")
    private int rows;

    @JsonProperty("symbols")
    private Map<String, SymbolConfig> symbols;

    @JsonProperty("probabilities")
    private Probabilities probabilities;

    @JsonProperty("win_combinations")
    private Map<String, WinCombination> winCombinations;

    // Getters and setters
    public int getColumns() { return columns; }
    public int getRows() { return rows; }
    public Map<String, SymbolConfig> getSymbols() { return symbols; }
    public Probabilities getProbabilities() { return probabilities; }
    public Map<String, WinCombination> getWinCombinations() { return winCombinations; }
}

class SymbolConfig {
    private Double rewardMultiplier;
    private String type;
    private String impact;
    private Integer extra;

    // Getters and setters with JsonProperty for different field names
    @JsonProperty("reward_multiplier")
    public Double getRewardMultiplier() { return rewardMultiplier; }
    public String getType() { return type; }
    public String getImpact() { return impact; }
    public Integer getExtra() { return extra; }
}

class Probabilities {
    private List<StandardSymbolProbability> standardSymbols;
    private BonusSymbols bonusSymbols;

    // Getters and setters
    @JsonProperty("standard_symbols")
    public List<StandardSymbolProbability> getStandardSymbols() { return standardSymbols; }
    @JsonProperty("bonus_symbols")
    public BonusSymbols getBonusSymbols() { return bonusSymbols; }
}

class StandardSymbolProbability {
    private int column;
    private int row;
    private Map<String, Integer> symbols;

    // Getters and setters
    public int getColumn() { return column; }
    public int getRow() { return row; }
    public Map<String, Integer> getSymbols() { return symbols; }
}

class BonusSymbols {
    private Map<String, Integer> symbols;

    // Getters and setters
    public Map<String, Integer> getSymbols() { return symbols; }
}

class WinCombination {
    private double rewardMultiplier;
    private String when;
    private Integer count;
    private String group;
    private List<List<String>> coveredAreas;

    // Getters and setters with JsonProperty
    @JsonProperty("reward_multiplier")
    public double getRewardMultiplier() { return rewardMultiplier; }
    public String getWhen() { return when; }
    public Integer getCount() { return count; }
    public String getGroup() { return group; }
    @JsonProperty("covered_areas")
    public List<List<String>> getCoveredAreas() { return coveredAreas; }
}