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

### 图表所需数据
#### 选手间H2H
```
api: /api/h2h/vsPlayer?player1Id=XXX&player2Id=XXX
```
返回一个JSONObject

demo:
```
{
    "player1Name": "哈皮柠檬",
    "player1Rank": 2,
    "player2Name": "roger裙裙",
    "player2Rank": 5,
    "winsOfPlayer1": 2,
    "winsOfPlayer2": 1
    "result": [
        {
            "result": "哈皮柠檬(114) d. roger裙裙(114)",
            "score": "1 - 0",
            "eventLevel": "PM",
            "round": "R16",
            "season": "第1季",
            "eventName": "北京公开赛",
            "resultFlag": 1,
            "matchMode": "普通"
        },
        {
            "result": "roger裙裙(9) d. 哈皮柠檬(4)",
            "score": "1 - 0",
            "eventLevel": "125K",
            "round": "F",
            "season": 第1季,
            "eventName": "小天使125K",
            "resultFlag": 2,
            "matchMode": "普通"
        },
        {
            "result": "哈皮柠檬(4) d. roger裙裙(7)",
            "score": "1 - 0",
            "eventLevel": "GS",
            "round": "QF",
            "season": 第1季,
            "eventName": "乌干达冰壶公开赛",
            "resultFlag": 1,
            "matchMode": "普通"
        }
    ]
}
```
字段解释
| 字段名 | 取值类型 | 对应图表位置 | 备注 |
| ----- | ----- | ----- | ----- |
| player1Name | String | 对应于用户在左边下拉框中选择的选手姓名 与player1Rank一起展示在左边下拉框的下面 环形图的左边 |  |
| player1Rank | int | 与player1Name一起展示在左边下拉框的下面 环形图的左边 |  |
| player2Name | String | 对应于用户在右边下拉框中选择的选手姓名 与player2Rank一起展示在右边下拉框的下面 环形图的右边 |  |
| player2Rank | int | 与player2Name一起展示在右边下拉框的下面 环形图的右边 |  |
| winsOfPlayer1 | int | 展示在环形图的左边 | 环形图的数据来源 |
| winsOfPlayer2 | int | 展示在环形图的右边 | 环形图的数据来源 |
| result | JsonArray |  | 表格的数据来源 array里的每个json代表表格里的一行数据 以下字段从左到右展示在表格中 如果result为空 不展示表格 |
| season | String | 展示在表格的第一列 |  |
| eventLevel | String | 展示在表格的第二列 |  |
| matchMode | String | 展示在表格的第三列 |  |
| eventName | Stirng | 展示在表格的第四列 |  |
| round | String | 展示在表格的第五列 |  |
| result | String | 展示在表格的第六列 |  |
| score | String | 展示在表格的第七列 |  |
| resultFlag | int | 无 | 不同的值对应表格中这一行的背景色 0 - 背景色为白色 1 - 背景色为player1对应的颜色 参考图中为蓝色 2 - 背景色为player2对应的颜色 参考图中为粉色 |

#### 对阵topN H2H
```
api: /api/h2h/vsTopN?playerId=XXX&topN=XXX
```
返回一个JSONObject

demo:
```
{
    "playerName": "后手如来",
    "playerRank": 3,
    "winsOfPlayer": 1, 
    "winsOfTopN": 1,
    "result": [
        {
            "result": "后手如来(16) d. 莉莉金(9)",
            "score": "1 - 0",
            "eventLevel": "P5",
            "round": "SF",
            "season": 1,
            "eventName": "武汉公开赛",
            "resultFlag": 1,
            "matchMode": "普通"
        },
        {
            "result": "黄大宪(1) d. 后手如来(16)",
            "score": "1 - 0",
            "eventLevel": "P5",
            "round": "F",
            "season": 1,
            "eventName": "武汉公开赛",
            "resultFlag": 2,
            "matchMode": "普通"
        }
    ]
}
```
字段解释
| 字段名 | 取值类型 | 对应图表位置 | 备注 |
| ----- | ----- | ----- | ----- |
| playerName | String | 对应于用户在左边下拉框中选择的选手姓名 与playerRank一起展示在左边下拉框的下面 环形图的左边 |  |
| playerRank | int | 与playerName一起展示在左边下拉框的下面 环形图的左边 |  |
| winsOfPlayer | int | 展示在环形图的左边 | 环形图的数据来源 |
| winsOfTopN | int | 展示在环形图的右边 | 环形图的数据来源 |
| result | JsonArray |  | 以下同#选手间H2H 表格的数据来源 array里的每个json代表表格里的一行数据 以下字段从左到右展示在表格中 如果result为空 不展示表格 |
| season | String | 展示在表格的第一列 |  |
| eventLevel | String | 展示在表格的第二列 |  |
| matchMode | String | 展示在表格的第三列 |  |
| eventName | Stirng | 展示在表格的第四列 |  |
| round | String | 展示在表格的第五列 |  |
| result | String | 展示在表格的第六列 |  |
| score | String | 展示在表格的第七列 |  |
| resultFlag | int | 无 | 不同的值对应表格中这一行的背景色 0 - 背景色为白色 1 - 背景色为player1对应的颜色 参考图中为蓝色 2 - 背景色为player2对应的颜色 参考图中为粉色 |
