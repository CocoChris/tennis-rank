### Get接口
/api/rank

### 字段说明
```
{
    "result": [
        {
            "winRateOfCareer": 0.58, 
            "reachNewHighsOfRank": 0,
            "playerName": "心机撕",
            "rankChange": -1,
            "liveRank": 14,
            "currGrade": "",
            "winsOfCareer": 21,
            "lossesOfCareerVsTop10": 2,
            "minValidPts": 22,
            "winsOfCareerVsTop10": 4,
            "stateOfCurrWeek": 0,
            "winsOfCurrSeason": 0,
            "highestRank": 10,
            "minusPts": 0,
            "winRateOfCareerVsTop10": 0.67,
            "winRateOfCurrSeason": 0,
            "numOfTitlesOfCareer": 1,
            "numOfEntriesOfWholePeriod": 16,
            "totalPts": 1598,
            "lossesOfCareer": 15,
            "plusPts": 0,
            "lossesOfCurrSeason": 0
        }
        , ...
    ]
    "updateTime": "2019-02-23 00:08:23",
    "id": 1,
    "jsonrpc": "2.0"
}
```

```
int liveRank;
int rankChange;
int highestRank;
int reachNewHighsOfRank;
String playerName;
int totalPts;
int minusPts;
int plusPts;
int minValidPts;
String currGrade;
int stateOfCurrWeek;
int numOfEntriesOfWholePeriod;
int numOfTitlesOfCareer;
int winsOfCurrSeason;
int lossesOfCurrSeason;
double winRateOfCurrSeason;
int winsOfCareer;
int lossesOfCareer;
double winRateOfCareer;
int winsOfCareerVsTop10;
int lossesOfCareerVsTop10;
double winRateOfCareerVsTop10;
}

```

#### 从左到右显示以下字段 具体参考  https://coric.top/zh/rank/wta/s/year
| 字段名 | web页面上的中文名称 | 取值类型 | 备注 |
| ------ | ------ | ------ | ------ |
| liveRank | 排名 | int | |
| rankChange | 升降 | int | |
| reachNewHIghsOfRank | 无 | int | 取值[0，1] 等于1时, 将liveRank的单元格背景变黑 字体变白 |
| playerName | 选手 | String | |
| totalPts | 积分 | int | |
| minusPts | - | int | 加个负号显示在表格里 |
| plusPts | + | int | 加个加号显示在表格里 |
| minValidPts | 起计分 | int | |
| currGrade | 本周赛事 | String | |
| stateOfCurrWeek | 无 | int | 取值[0, 1, 2] 等于0时对应那一行背景不变 等于1时背景变成灰色 等于2时背景被称浅蓝色 |
| numOfEntriesOfWholePeriod | 参赛数 | int | |
| numOfTitlesOfCareer | 冠军数 | int | |
| winsOfCurrSeason | 本赛季战绩 | int | 和下一个字段在同一个单元格里显示 |
| lossesOfCurrSeason | 本赛季战绩 | int | 和上一个字段在同一个单元格里显示 中间以-符号连接 例如"2-1" |
| winRateOfCurrSeason | 胜率 | double | 以百分数形式展示 |
| winsOfCareer | 总战绩 | int | 和下一个字段在同一个单元格里显示 |
| lossesOfCareer | 总战绩 | int | 和上一个字段在同一个单元格里显示 中间以-符号连接 例如"2-1" |
| winRateOfCareer | 胜率 | double | 以百分数形式展示 |
| winsOfCareerVsTop10 | 对阵top10战绩 | int | 和下一个字段在同一个单元格里显示 |
| lossesOfCareerVsTop10 | 对阵top10战绩 | int | 和上一个字段在同一个单元格里显示 中间以-符号连接 例如"2-1" |
| winRateOfCareerVsTop10 | 胜率 | double | 以百分数形式展示 |
