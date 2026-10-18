export const timeRangeShortcuts = [
    {
        text: '15分钟内',
        value: () => {
            const end = new Date()
            const start = new Date()
            start.setMinutes(start.getMinutes() - 15)
            return [start, end]
        },
    },
    {
        text: '30分钟内',
        value: () => {
            const end = new Date()
            const start = new Date()
            start.setMinutes(start.getMinutes() - 30)
            return [start, end]
        },
    },
    {
        text: '1 小时内',
        value: () => {
            const end = new Date()
            const start = new Date()
            start.setHours(start.getHours() - 1)
            return [start, end]
        },
    },
    {
        text: '3 小时内',
        value: () => {
            const end = new Date()
            const start = new Date()
            start.setHours(start.getHours() - 3)
            return [start, end]
        },
    },
    {
        text: '6 小时内',
        value: () => {
            const end = new Date()
            const start = new Date()
            start.setHours(start.getHours() - 6)
            return [start, end]
        },
    },
    {
        text: '12小时内',
        value: () => {
            const end = new Date()
            const start = new Date()
            start.setHours(start.getHours() - 12)
            return [start, end]
        },
    },
    {
        text: '24小时内',
        value: () => {
            const end = new Date()
            const start = new Date()
            start.setDate(start.getDate() - 1)
            return [start, end]
        },
    },
    {
        text: '2 天内',
        value: () => {
            const end = new Date()
            const start = new Date()
            start.setDate(start.getDate() - 2)
            return [start, end]
        },
    },
    {
        text: '1 个月内',
        value: () => {
            const end = new Date()
            const start = new Date()
            start.setMonth(start.getMonth() - 1)
            return [start, end]
        },
    },
]
