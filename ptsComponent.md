### 带搜索功能的下拉选择框的取值列表
```
api: /api/playerList
```
返回一个JSONArray

demo:
```
[
    {
        id: 1001, 
        name: XXXX
    },
    {
        ...
    }
]
```

### 图标所需数据
#### Get接口 
```
api: /api/h2h/vsPlayer?player1Id=XXX&player2Id=XXX
```
返回一个JSONObject

demo:
```
{
    "playerId": 2,
    "playerName": "Marc",
    "currRank": 2,
    "highestRank": 1,
    "singleWinsOfCurrSeason": 8,
    "singleLossesOfCurrSeason": 1,
    "doubleWinsOfCurrSeason": 1,
    "doubleLossesOfCurrSeason": 1,
    "ptsComponentInEventLevel": {
        "T1": {
            "components": [
                {
                    "date": "2020-04-18",
                    "eventLevel": "T1",
                    "round": "W",
                    "eventName": "天马山大满贯赛",
                    "eventType": "单打",
                    "pts": 2000
                },
                {
                    "date": "2019-06-22",
                    "eventLevel": "T1",
                    "round": "SF",
                    "eventName": "天马山大满贯赛",
                    "eventType": "单打",
                    "pts": 720
                },
                {
                    "date": "2019-11-16",
                    "eventLevel": "T1",
                    "round": "SF",
                    "eventName": "阳澄湖半岛大满贯赛",
                    "eventType": "单打",
                    "pts": 720
                }
            ],
            "eventLevel": "大满贯",
            "totalPts": 3440
        },
        "YEC": {
            "components": [
                {
                    "date": "2019-12-15",
                    "eventLevel": "YEC",
                    "round": "W",
                    "eventName": "单打年终总决赛",
                    "eventType": "单打",
                    "pts": 1500
                }
            ],
            "eventLevel": "大年终",
            "totalPts": 1500
        },
        "T2": {
            "components": [
                {
                    "date": "2020-04-05",
                    "eventLevel": "T2",
                    "round": "F",
                    "eventName": "山河无恙杯赛",
                    "eventType": "单打",
                    "pts": 600
                },
                {
                    "date": "2019-05-11",
                    "eventLevel": "T2",
                    "round": "F",
                    "eventName": "天使之翼大师赛",
                    "eventType": "双打",
                    "pts": 540
                },
                {
                    "date": "2019-10-20",
                    "eventLevel": "T2",
                    "round": "QF",
                    "eventName": "兄弟同心大师赛",
                    "eventType": "单打",
                    "pts": 150
                },
                {
                    "date": "2020-05-01",
                    "eventLevel": "T2",
                    "round": "QF",
                    "eventName": "翠微鸣柳大师赛",
                    "eventType": "双打",
                    "pts": 120
                }
            ],
            "eventLevel": "大师赛",
            "totalPts": 1410
        },
        "T3": {
            "components": [
                {
                    "date": "2019-12-01",
                    "eventLevel": "T3",
                    "round": "SF",
                    "eventName": "冰雪奇缘杯赛",
                    "eventType": "团体",
                    "pts": 180
                },
                {
                    "date": "2019-11-03",
                    "eventLevel": "T3",
                    "round": "QF",
                    "eventName": "枫与火之歌赛",
                    "eventType": "双打",
                    "pts": 50
                }
            ],
            "eventLevel": "国际赛",
            "totalPts": 230
        },
        "totalPts": 6580
    },
    "ptsComponentInEventDate": {
        "2019.05": {
            "components": [
                {
                    "date": "2019-05-11",
                    "eventLevel": "T2",
                    "round": "F",
                    "eventName": "天使之翼大师赛",
                    "eventType": "双打",
                    "pts": 540
                }
            ],
            "totalPts": 540
        },
        "2019.06": {
            "components": [
                {
                    "date": "2019-06-22",
                    "eventLevel": "T1",
                    "round": "SF",
                    "eventName": "天马山大满贯赛",
                    "eventType": "单打",
                    "pts": 720
                }
            ],
            "totalPts": 720
        },
        "2019.10": {
            "components": [
                {
                    "date": "2019-10-20",
                    "eventLevel": "T2",
                    "round": "QF",
                    "eventName": "兄弟同心大师赛",
                    "eventType": "单打",
                    "pts": 150
                }
            ],
            "totalPts": 150
        },
        "2019.11": {
            "components": [
                {
                    "date": "2019-11-03",
                    "eventLevel": "T3",
                    "round": "QF",
                    "eventName": "枫与火之歌赛",
                    "eventType": "双打",
                    "pts": 50
                },
                {
                    "date": "2019-11-16",
                    "eventLevel": "T1",
                    "round": "SF",
                    "eventName": "阳澄湖半岛大满贯赛",
                    "eventType": "单打",
                    "pts": 720
                }
            ],
            "totalPts": 770
        },
        "2019.12": {
            "components": [
                {
                    "date": "2019-12-01",
                    "eventLevel": "T3",
                    "round": "SF",
                    "eventName": "冰雪奇缘杯赛",
                    "eventType": "团体",
                    "pts": 180
                },
                {
                    "date": "2019-12-15",
                    "eventLevel": "YEC",
                    "round": "W",
                    "eventName": "单打年终总决赛",
                    "eventType": "单打",
                    "pts": 1500
                }
            ],
            "totalPts": 1680
        },
        "2020.04": {
            "components": [
                {
                    "date": "2020-04-05",
                    "eventLevel": "T2",
                    "round": "F",
                    "eventName": "山河无恙杯赛",
                    "eventType": "单打",
                    "pts": 600
                },
                {
                    "date": "2020-04-18",
                    "eventLevel": "T1",
                    "round": "W",
                    "eventName": "天马山大满贯赛",
                    "eventType": "单打",
                    "pts": 2000
                }
            ],
            "totalPts": 2600
        },
        "2020.05": {
            "components": [
                {
                    "date": "2020-05-01",
                    "eventLevel": "T2",
                    "round": "QF",
                    "eventName": "翠微鸣柳大师赛",
                    "eventType": "双打",
                    "pts": 120
                }
            ],
            "totalPts": 120
        }
    },
    "rankHistory": {
        "phase": [
            "S1W2",
            "S1W3",
            "S1W4",
            "S1W5",
            "S1W6",
            "S1W7",
            "S1W8",
            "S1W9",
            "S1W10",
            "S1W11",
            "S1W12",
            "S1W13",
            "S1W14",
            "S1W15",
            "S2W0",
            "S2W1",
            "S2W2",
            "S2W3",
            "S2W4",
            "S2W5",
            "S2W6"
        ],
        "rank": [
            16,
            21,
            26,
            1,
            1,
            1,
            1,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            1,
            1,
            1,
            1,
            1,
            2,
            2
        ]
    },
    "singleWinRateDist": {
        "winRate": [
            0,
            0.5,
            0.6666666666666666,
            0.75,
            0.8,
            0.8333333333333334,
            0.8571428571428571,
            0.8888888888888888
        ],
        "opponentRank": [
            4,
            5,
            6,
            22,
            26,
            32,
            87,
            999
        ]
    }
}
```

字段解释
| 字段名 | 取值类型 | 对应图表位置 |
| ----- | ----- | ----- |
| playerName | String | 选手ID |
| currRank | int | 当前排名 |
| highestRank | int | 历史最高排名 |
| singleWinsOfCurrSeason | int | 本赛季单打胜负环形图 |  |
| singleLossesOfCurrSeason | int | 本赛季单打胜负环形图 |  |
| doubleWinsOfCurrSeason | int | 本赛季双打胜负环形图 |  |
| doubleLossesOfCurrSeason | int | 本赛季双打胜负环形图 |  |
| ptsComponentInEventLevel | JSONObject | 积分构成表格和环形图 |
| ptsComponentInEventDate | JSONObject | 积分构成-按失效时间 柱状图 |
| rankHistory | JSONObject | 排名走势图 |
| singleWinRateDist | JSONObject | 单打胜率图 |
