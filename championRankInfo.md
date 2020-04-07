### Get接口
/api/championRank

### 字段说明
```
{
    "result": [
        {
            "ptsOfGS1": 130,
            "playerName": "技术不到位",
            "rankChange": 0,
            "currGrade": "嘻嘻公开赛 QF",
            "ptsOfNo7": 50,
            "ptsOfAF": 0,
            "ptsOfNo8": 1,
            "minValidPts": 1,
            "ptsOfNo3": 100,
            "ptsOfNo4": 60,
            "ptsOfNo5": 60,
            "ptsOfPM1": 215,
            "stateOfCurrWeek": 1,
            "ptsOfET": 0,
            "ptsOfNo6": 57,
            "ptsOfPM2": 390,
            "minusPts": 0,
            "ptsOfNo1": 180,
            "ptsOfNo2": 100,
            "rank": 9,
            "ptsOfGS2": 0,
            "totalPts": 1343,
            "plusPts": 60
        }
        , ...
    ]
    "updateTime": "2019-02-23 00:08:23",
    "id": 1,
    "jsonrpc": "2.0"
}
```

```
int rank;
int rankChange;
String playerName;
int totalPts;
int plusPts;
int minValidPts;
String currGrade;
int stateOfCurrWeek;
int ptsOfGS1;
int ptsOfGS2;
int ptsOfPM1;
int ptsOfPM2;
int ptsOfAF;
int ptsOfET;
int ptsOfNo1;
int ptsOfNo2;
int ptsOfNo3;
int ptsOfNo4;
int ptsOfNo5;
int ptsOfNo6;
int ptsOfNo7;
int ptsOfNo8;
```

#### 从左到右显示以下字段 具体参考  https://coric.top/zh/rank/wta/s/year
| 字段名 | web页面上的中文名称 | 取值类型 | 备注 |
| ------ | ------ | ------ | ------ |
| rank | 排名 | int | |
| rankChange | 升降 | int | |
| playerName | 选手 | String | |
| totalPts | 积分 | int | |
| plusPts | + | int | 加个加号显示在表格里 |
| minValidPts | 起计分 | int | |
| currGrade | 本周赛事 | String | |
| stateOfCurrWeek | 无 | int | 取值[0, 1, 2] 等于0时对应那一行背景不变 等于1时背景变成灰色 等于2时背景被称浅蓝色 |
| ptsOfGS1 | 中壶 | int | |
| ptsOfGS2 | 乌壶 | int | |
| ptsOfPM1 | IW | int | |
| ptsOfPM2 | 马德里 | int | |
| ptsOfAF | 大年终 | int | |
| ptsOfET | 小年终 | int | |
| ptsOfNo1 | No.1 | int | |
| ptsOfNo2 | No.2 | int | |
| ptsOfNo3 | No.3 | int | |
| ptsOfNo4 | No.4 | int | |
| ptsOfNo5 | No.5 | int | |
| ptsOfNo6 | No.6 | int | |
| ptsOfNo7 | No.7 | int | |
| ptsOfNo8 | No.8 | int | |
