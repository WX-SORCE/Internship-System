import React, { useRef, useEffect,useState } from 'react';
import * as echarts from 'echarts';
import { topratios } from "@/api";
import { useStateUserInfo } from "@/store/hooks";
// import './LineChart.css';

const LineChart = () => {
    const chartRef = useRef(null);
    const userInfo = useStateUserInfo()

    const [topratiosData, setTopratiosData] = useState([
        { name: "基金", value: 1 },
        { name: "股票", value: 3 },
        { name: "理财", value: 4 }
    ]);
    // const [datas, setDatas] = useState([
    //     1, 2, 3, 3, 3, 34, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 56, 6, 7, 54, 37, 67, 7
    // ]);
    // const [colors] = useState(["#0092f6", "#00d4c7", "#aecb56"]);
    // const [sonmed, setSonmed] = useState([]);

    useEffect(() => {
        // console.log("历史趋势", res)
        topratios(userInfo).then((res) => {
            if (res.data.length !== 0) {
                setTopratiosData(res.data)
                // const response = {
                //     "code": 1,
                //     "msg": "success",
                //     "data": [
                //         {
                //             "returnRate": 3.6316,
                //             "portfolioName": "组合3"
                //         },
                //         {
                //             "returnRate": 0.8506,
                //             "portfolioName": "组合2"
                //         }
                //     ]

                // };
                // console.log("走了？")
                // const returnRates = res.data.map(item => item.returnRate);
                // const portfolioNames = res.data.map(item => item.portfolioName);
                // setreturnRates(returnRates)
                // setportfolioNames(portfolioNames)
            }
        })
    }, []);
    useEffect(() => {
        renderChart()
    }, []);

    const renderChart = () => {
        const option = {
            color: [
                "rgba(254, 234, 129, 0.9)",
                "rgba(113, 246, 181, 0.9)",
                "rgba(151, 134, 252, 0.9)",
                "rgba(249, 119, 128, 0.9)"
            ],
            grid: {
                left: -100,
                top: 50,
                bottom: 10,
                right: 10,
                containLabel: true
            },
            tooltip: {
                trigger: "item",
                formatter: "{b} : {c} ({d}%)"
            },
            legend: {
                type: "scroll",
                orient: "vertical",
                top: "center",
                right: "15",
                itemWidth: 16,
                itemHeight: 8,
                itemGap: 16,
                textStyle: {
                    color: "#A3E2F4",
                    fontSize: 12,
                    fontWeight: 0
                },
                data: ["基金", "股票", "理财"]
            },
            polar: {},
            angleAxis: {
                interval: 1,
                type: "category",
                data: [],
                z: 10,
                axisLine: {
                    show: false,
                    lineStyle: {
                        color: "#0B4A6B",
                        width: 1,
                        type: "solid"
                    }
                },
                axisLabel: {
                    interval: 0,
                    show: true,
                    color: "#0B4A6B",
                    margin: 8,
                    fontSize: 16
                }
            },
            radiusAxis: {
                min: 40,
                max: 120,
                interval: 20,
                axisLine: {
                    show: false,
                    lineStyle: {
                        color: "#0B3E5E",
                        width: 1,
                        type: "solid"
                    }
                },
                axisLabel: {
                    formatter: "{value} %",
                    show: false,
                    padding: [0, 0, 20, 0],
                    color: "#0B3E5E",
                    fontSize: 16
                },
                splitLine: {
                    lineStyle: {
                        color: "#0B3E5E",
                        width: 2,
                        type: "solid"
                    }
                }
            },
            calculable: true,
            series: [
                {
                    type: "pie",
                    radius: ["5%", "10%"],
                    hoverAnimation: false,
                    labelLine: {
                        normal: {
                            show: false,
                            length: 30,
                            length2: 55
                        },
                        emphasis: {
                            show: false
                        }
                    },
                    data: [
                        {
                            name: "",
                            value: 0,
                            itemStyle: {
                                normal: {
                                    color: "#0B4A6B"
                                }
                            }
                        }
                    ]
                },
                {
                    type: "pie",
                    radius: ["90%", "95%"],
                    hoverAnimation: false,
                    labelLine: {
                        normal: {
                            show: false,
                            length: 30,
                            length2: 55
                        },
                        emphasis: {
                            show: false
                        }
                    },

                    data: [
                        {
                            name: "",
                            value: 0,
                            itemStyle: {
                                normal: {
                                    color: "#0B4A6B"
                                }
                            }
                        }
                    ]
                },
                {
                    stack: "a",
                    type: "pie",
                    radius: ["20%", "80%"],
                    roseType: "area",
                    zlevel: 10,
                    label: {
                        normal: {
                            show: true,
                            formatter: "{c}",
                            textStyle: {
                                fontSize: 12
                            },
                            position: "outside"
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    labelLine: {
                        normal: {
                            show: true,
                            length: 20,
                            length2: 55
                        },
                        emphasis: {
                            show: false
                        }
                    },
                    data: topratiosData
                }
            ]
        };

        if (chartRef.current) {
            const chartInstance = echarts.init(chartRef.current);
            chartInstance.setOption(option);

            const handleResize = () => {
                chartInstance.resize();
            };

            window.addEventListener('resize', handleResize);

            return () => {
                window.removeEventListener('resize', handleResize);
                echarts.dispose(chartInstance);
            };
        }
    };

    return (
        <div style={{ width: '100%' }}>
            <div ref={chartRef} id="chart" style={{ width: '100%', height: '335px' }}></div>
        </div>
    );
};

export default LineChart;