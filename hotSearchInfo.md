### 热搜榜
```
api: /api/home/hotSearch
```
返回一个JSONObject

demo:
```
{
    "后手如来": {
        "searchCount": 76,
        "increment": 76,
        "rank": 3
    },
    "哈皮柠檬": {
        "searchCount": 55,
        "increment": 55,
        "rank": 5
    },
    "小狼": {
        "searchCount": 37,
        "increment": 37,
        "rank": 8
    },
    "GOCHA123": {
        "searchCount": 29,
        "increment": 29,
        "rank": 9
    },
    "黄大宪": {
        "searchCount": 28,
        "increment": 28,
        "rank": 10
    },
    "技术不到位": {
        "searchCount": 142,
        "increment": 142,
        "rank": 1
    },
    "心机撕": {
        "searchCount": 88,
        "increment": 88,
        "rank": 2
    },
    "Reputation": {
        "searchCount": 49,
        "increment": 49,
        "rank": 6
    },
    "网球吧桔梗小维": {
        "searchCount": 40,
        "increment": 40,
        "rank": 7
    },
    "天才少女焉会": {
        "searchCount": 58,
        "increment": 58,
        "rank": 4
    }
}
```


字段解释
| 字段名 | 取值类型 | 对应图表位置 | 备注 |
| ----- | ----- | ----- | ----- |
| rank | int | 表格的第1列：排名 | 表格按照rank从小到大排列 |
| playerName(JSONObject的key) | String | 表格的第2列：选手 |  |
| searchCount | int | 表格的第3列：搜索热度 |  |
| increment | int | 表格的第4列：新增热度 |  |
