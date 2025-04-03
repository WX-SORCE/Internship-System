import React, { useRef, useEffect, useState } from 'react';
import * as echarts from 'echarts';
import { useStateUserInfo } from "@/store/hooks";
import { getThreeInfo } from "@/api";

// import './LineChart.css';

const RadarChart = () => {
    const chartRef = useRef(null);
    const userInfo = useStateUserInfo()

    // topratios(userInfo).then((res) => {

    // const [indicatorsonmed, setIndicatorsonmed] = useState({});
    // const [legendData, setLegendData] = useState(['1', '2']);
    // const [datas, setDatas] = useState([
    //     1, 2, 3, 3, 3, 34, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 56, 6, 7, 54, 37, 67, 7
    // ]);
    // const [colors, setColors] = useState(["#0092f6", "#00d4c7", "#aecb56"]);
    const [dataArrscrore, setDataArrscrore] = useState([7, 8, 9]);
    const [indicator, setIndicator] = useState([
        {
            text: "收入",
            max: 10,
        },
        {
            text: "风险",
            max: 10,
        },
        {
            text: "整体",
            max: 10,
        },
    ]);

    useEffect(() => {
        // console.log("历史趋势", res)
        getThreeInfo(userInfo).then((res) => {
            if (Object.keys(res.data).length !== 0) {
                const valuesList = [
                    res.data.riskLevel,
                    res.data.incomeLevel,
                    res.data.totalAssets
                ];
                setDataArrscrore(valuesList)
            }
        })

    }, []);
    useEffect(() => {
        renderChart()
    }, [dataArrscrore]);


    const renderChart = () => {
        const legendData = ["等级"];
        const dataArr = [
            {
                value: dataArrscrore,
                name: legendData[0],
                itemStyle: {
                    normal: {
                        lineStyle: {
                            color: "#4A99FF",
                        },
                        shadowColor: "#4A99FF",
                        shadowBlur: 10,
                    },
                },
                areaStyle: {
                    normal: {
                        color: {
                            type: "linear",
                            x: 0,
                            y: 0,
                            x2: 1,
                            y2: 1,
                            colorStops: [
                                {
                                    offset: 0,
                                    color: "#4A99FF",
                                },
                                {
                                    offset: 0.5,
                                    color: "rgba(0,0,0,0)",
                                },
                                {
                                    offset: 1,
                                    color: "#4A99FF",
                                },
                            ],
                            globalCoord: false,
                        },
                        opacity: 1,
                    },
                },
            },
        ];
        const colorArr = ["#fde987"];

        const option = {
            color: colorArr,
            legend: {
                orient: "vertical",
                icon: "circle",
                data: legendData,
                bottom: 35,
                right: 40,
                itemWidth: 14,
                itemHeight: 14,
                itemGap: 21,
                textStyle: {
                    fontSize: 14,
                    color: "#00E4FF",
                },
            },
            radar: {
                name: {
                    textStyle: {
                        color: "#fff",
                        fontSize: 16,
                    },
                },
                indicator: indicator,
                splitArea: {
                    show: true,
                    areaStyle: {
                        color: ["rgba(255,255,255,0)", "rgba(255,255,255,0)"],
                    },
                },
                axisLine: {
                    lineStyle: {
                        color: "#1D6B86",
                    },
                },
                splitLine: {
                    lineStyle: {
                        color: "#1D6B86",
                        width: 1,
                    },
                },
            },
            series: [
                {
                    type: "radar",
                    symbolSize: 8,
                    data: dataArr,
                },
            ],
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

export default RadarChart;