import React, { useRef, useEffect, useState } from "react";
import * as echarts from "echarts";
import { portfoliosclientnew } from "@/api";
import { useStateUserInfo } from "@/store/hooks";


const LineChart = () => {
  const chartRef = useRef(null);
  const [keysData, setKeysData] = useState([
    "3-27", '3-26', '3-25', "3-27", '3-26', '3-25', "3-27",
  ]);
  const [valuesData, setValuesData] = useState([1, 2, 3, 5, 6, 2, 3]);
  // const [sonmed, setSonmed] = useState([]);
  // const [colors, setColors] = useState(["#0092f6", "#00d4c7", "#aecb56"]);
  // const [max1, setMax1] = useState("");
  // const [min1, setMin1] = useState("");
  const userInfo = useStateUserInfo()

  useEffect(() => {
    portfoliosclientnew(userInfo).then((res) => {
      console.log("历史趋势", res)
      if (Object.keys(res.data).length !== 0) {
        // const response = {
        //   "code": 1,
        //   "msg": "success",
        //   "data": {
        //     "2025-03-23": 152.3,
        //     "2025-03-24": 153.0,
        //     "2025-03-25": 153.5,
        //     "2025-03-26": 153.2,
        //     "2025-03-27": 153.7,
        //     "2025-03-28": 198.915
        //   }
        // };
        // console.log("走了？")
        const keysData = Object.keys(res.data);
        const valuesData = Object.values(res.data);

        console.log(keysData, valuesData)
        setKeysData(keysData)
        setValuesData(valuesData)
      }
    })
    //   // setLegendData(["1", "2"]);

    //   const handleGetTransCharts = (event) => {
    //     const res = event.detail;
    //     setSonmed(res);
    //     console.log("电能质量接收", res);
    //     const max = Math.max(...res);
    //     const min = Math.min(...res);
    //     setMax1(max + 0.5);
    //     setMin1(min - 0.5);
    //     setDatas(res);
    //     const chartContainer = echarts.getInstanceByDom(chartRef.current);
    //     if (chartContainer) {
    //       echarts.dispose(chartContainer);
    //     }
    // setTimeout(() => {
    // }, 1000)

    // };

    //   window.addEventListener("getTransCharts1", handleGetTransCharts);

    //   return () => {
    //     window.removeEventListener("getTransCharts1", handleGetTransCharts);
    //   };
  }, []);
  useEffect(() => {
    renderChart()
  }, [keysData,valuesData]);


  const renderChart = () => {
    const option = {
      tooltip: {
        trigger: "axis",
        axisPointer: {
          lineStyle: {
            color: {
              type: "linear",
              x: 0,
              y: 0,
              x2: 0,
              y2: 1,
              colorStops: [
                {
                  offset: 0,
                  color: "rgba(0, 255, 233,0)",
                },
                {
                  offset: 0.5,
                  color: "rgba(255, 255, 255,1)",
                },
                {
                  offset: 1,
                  color: "rgba(0, 255, 233,0)",
                },
              ],
              global: false,
            },
          },
        },
      },
      grid: {
        top: "15%",
        left: "10%",
        right: "5%",
        bottom: "15%",
      },
      legend: {
        itemGap: 50,
        data: ["人员出入总数"],
        textStyle: {
          color: "#f9f9f9",
          borderColor: "#fff",
        },
      },
      xAxis: [
        {
          type: "category",
          boundaryGap: true,
          axisLine: {
            show: true,
            lineStyle: {
              color: "#f9f9f9",
            },
          },
          axisLabel: {
            textStyle: {
              color: "#d1e6eb",
              margin: 15,
            },
          },
          axisTick: {
            show: false,
          },
          data: keysData,
        },
      ],
      yAxis: [
        {
          type: "value",
          // min: min1,
          // max: max1,
          splitNumber: 7,
          splitLine: {
            show: true,
            lineStyle: {
              color: "#0a3256",
            },
          },
          axisLine: {
            show: false,
          },
          axisLabel: {
            margin: 20,
            textStyle: {
              color: "#d1e6eb",
            },
          },
          axisTick: {
            show: false,
          },
        },
      ],
      series: [
        {
          name: "总价",
          type: "line",
          smooth: true,
          showAllSymbol: true,
          symbol: "emptyCircle",
          symbolSize: 6,
          lineStyle: {
            normal: {
              color: "#28ffb3",
            },
            borderColor: "#f0f",
          },
          label: {
            show: false,
          },
          itemStyle: {
            normal: {
              color: "#28ffb3",
            },
          },
          tooltip: {
            show: true,
          },
          markLine: {
            silent: true,
            symbol: "none",
            label: {
              position: "middle",
              formatter: "{b}",
            },
            // data: [
            //   {
            //     name: "标准",
            //     yAxis: 10,
            //     lineStyle: {
            //       color: "#ffc832",
            //     },
            //     label: {
            //       position: "end",
            //       formatter: "{b}\n {c}",
            //     },
            //   },
            // ],
          },
          areaStyle: {
            normal: {
              color: new echarts.graphic.LinearGradient(
                0,
                0,
                0,
                1,
                [
                  {
                    offset: 0,
                    color: "rgba(0,154,120,1)",
                  },
                  {
                    offset: 1,
                    color: "rgba(0,0,0, 0)",
                  },
                ],
                false
              ),
              shadowColor: "rgba(53,142,215, 0.9)",
              shadowBlur: 20,
            },
          },
          data: valuesData,
        },
      ],
    };

    const chartDom = chartRef.current;
    const chartInstance = echarts.init(chartDom);
    chartInstance.setOption(option);

    const handleResize = () => {
      chartInstance.resize();
    };

    window.addEventListener("resize", handleResize);

    return () => {
      window.removeEventListener("resize", handleResize);
      echarts.dispose(chartInstance);
    };
  };

  return (
    <div style={{ width: "100%" }}>
      <div ref={chartRef} id="chart" style={{ width: "100%", height: "335px" }}></div>
    </div>
  );
};

export default LineChart;