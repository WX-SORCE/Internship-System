import React, { useRef, useEffect, useState } from 'react';
import * as echarts from 'echarts';
import { topfiveportfolio } from "@/api";
import { useStateUserInfo } from "@/store/hooks";


const Histogram2 = () => {
    const chartRef = useRef(null);
    // const [legendData] = useState(['1', '2']);
    const [returnRates, setreturnRates] = useState([43, 4, 4, 4, 4]);
    const [portfolioNames, setportfolioNames] = useState([
        "A组合",
        "B组合",
        "C组合",
        "D组合",
        "E组合",
    ]);

    const [colors] = useState(["#0092f6", "#00d4c7", "#aecb56"]);
    const userInfo = useStateUserInfo()

    useEffect(() => {
        // topfiveportfolio
        topfiveportfolio(userInfo).then((res) => {
            // console.log("历史趋势", res)
            if (res.data.length !== 0) {
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
                const returnRates = res.data.map(item => item.returnRate);
                const portfolioNames = res.data.map(item => item.portfolioName);
                setreturnRates(returnRates)
                setportfolioNames(portfolioNames)
            }
        })

    }, []);
    useEffect(() => {
        renderChart()
    }, [returnRates, portfolioNames]);

    // const addArrays = (arr1, arr2) => {
    //     if (arr1.length !== arr2.length) {
    //         throw new Error("数组长度不相等");
    //     }
    //     let result = [];
    //     for (let i = 0; i < arr1.length; i++) {
    //         result.push((Number(arr1[i]) + Number(arr2[i])).toFixed(4));
    //     }
    //     return result;
    // };

    const renderChart = () => {
        const category = portfolioNames;

        // const barData = datas.transfer || [1,2,3,4,5];
        const rateData = returnRates;
        // const barData2 = addArrays(barData, rateData);

        const option = {
            tooltip: {
                trigger: "axis",
                axisPointer: {
                    type: "shadow",
                    label: {
                        show: true,
                        backgroundColor: "#7B7DDC"
                    }
                }
            },
            legend: {
                data: ["收益率"],
                textStyle: {
                    color: "#B4B4B4"
                },
                // top: "4%"
            },
            grid: {
                // x: "12%",
                // width: "82%",
                // y: "15%"
            },
            xAxis: {
                data: category,
                axisLine: {
                    lineStyle: {
                        color: "#B4B4B4"
                    }
                },
                axisTick: {
                    show: false
                }
            },
            yAxis: [
                {
                    name: "收益率",
                    splitLine: { show: false },
                    axisLine: {
                        lineStyle: {
                            color: "#B4B4B4"
                        }
                    },
                    axisLabel: {
                        formatter: "{value}"
                    }
                },
                // {
                //     splitLine: { show: false },
                //     axisLine: {
                //         lineStyle: {
                //             color: "#B4B4B4"
                //         }
                //     },
                //     axisLabel: {
                //         formatter: "{value}"
                //     }
                // }
            ],
            series: [
                // {
                //     name: "总损耗",
                //     type: "line",
                //     smooth: true,
                //     showAllSymbol: true,
                //     symbol: "emptyCircle",
                //     symbolSize: 8,
                //     yAxisIndex: 1,
                //     itemStyle: {
                //         normal: {
                //             color: "#dd6a75"
                //         }
                //     },
                //     data: barData2
                // },
                {
                    name: "收益率",
                    type: "bar",
                    smooth: true,
                    showAllSymbol: true,
                    symbol: "emptyCircle",
                    symbolSize: 8,
                    barWidth: '20%',
                    // yAxisIndex: 1,
                    // stack: "总量",
                    itemStyle: {
                        normal: {
                            color: "#dd6a75"
                        }
                    },
                    data: rateData
                },
                // {
                //     name: "变压器损耗",
                //     type: "bar",
                //     barWidth: 10,
                //     stack: "总量",
                //     itemStyle: {
                //         normal: {
                //             barBorderRadius: 5,
                //             color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                //                 { offset: 0, color: "#3ee98e" },
                //                 { offset: 1, color: "#3EACE5" }
                //             ])
                //         }
                //     },
                //     data: barData
                // }
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
        <div style={{ width: '100%', marginTop: '20px' }}>
            <div ref={chartRef} id="chart" style={{ width: '100%', height: '335px' }}></div>
        </div>
    );
};

export default Histogram2;