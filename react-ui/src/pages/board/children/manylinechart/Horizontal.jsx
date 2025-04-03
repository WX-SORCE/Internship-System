import React, { useRef, useEffect, useState } from "react";
import * as echarts from "echarts";
import { useStateUserInfo } from "@/store/hooks";
import { portfoliosclient } from "@/api";

const Horizontal = () => {
  const chartRef = useRef(null);
  const [namesList, setNames] = useState(["A组合", "B组合", "c组合"]);
  const [lineXList, setLineX] = useState([
    "3-27",
    "3-26",
    "3-25",
  ]);
  const [valueList, setValue] = useState([[24, 43, 43], [1, 2, 3], [43, 64, 654]]);
  const [loading, setLoading] = useState(true);
  const userInfo = useStateUserInfo()

  useEffect(() => {
    portfoliosclient(userInfo).then((res) => {
      console.log("方案价值", res)
      if (Object.keys(res.data).length !== 0) {
        // 提取 names
        const namesList = Object.keys(res.data);
        console.log(namesList)
        setNames(namesList);

        // 提取 lineX
        const allDates = new Set();
        for (const name of namesList) {
          for (const item of res.data[name]) {
            const [date] = Object.keys(item);
            allDates.add(date);
          }
        }
        const lineXList = Array.from(allDates); // 将 Set 转换为数组
        console.log(lineXList)
        setLineX(lineXList)

        // 提取 value
        const valueList = namesList.map(name => {
          const itemMap = new Map(res.data[name].map(item => {
            const [date] = Object.keys(item);
            return [date, item[date]];
          }));
          return lineXList.map(date => itemMap.get(date) || 0);
        });
        console.log(valueList)
        setValue(valueList)
      }
      setLoading(false);
    })
  }, []);

  useEffect(() => {
    if (!loading) {
      renderChart()
    }
  }, [namesList, lineXList, valueList, loading]);

  const renderChart = () => {
    const color = ["rgba(65, 195, 171", "rgba(207, 192, 118", "rgba(221, 106, 117"];
    let lineY = [];

    for (let i = 0; i < namesList.length; i++) {
      let x = i;
      if (x > color.length - 1) {
        x = color.length - 1;
      }
      const data = {
        name: namesList[i],
        type: "line",
        color: color[x] + ")",
        smooth: true,
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
                  color: color[x] + ", 0.3)",
                },
                {
                  offset: 0.8,
                  color: color[x] + ", 0)",
                },
              ],
              false
            ),
            shadowColor: "rgba(0, 0, 0, 0.1)",
            shadowBlur: 10,
          },
        },
        symbol: "circle",
        symbolSize: 5,
        data: valueList[i],
      };
      lineY.push(data);
    }

    const option = {
      // backgroundColor: "#0d235e",
      tooltip: {
        trigger: "axis",
      },
      legend: {
        data: namesList,
        textStyle: {
          fontSize: 12,
          color: "rgb(0, 253, 255, 0.6)",
        },
        right: "4%",
      },
      grid: {
        top: "14%",
        left: "4%",
        right: "4%",
        bottom: "12%",
        containLabel: true,
      },
      xAxis: {
        type: "category",
        boundaryGap: false,
        data: lineXList,
        axisLabel: {
          textStyle: {
            color: "rgb(0, 253, 255, 0.6)",
          },
          formatter: function (params) {
            return params.split(" ")[0];
          },
        },
      },
      yAxis: {
        name: "总价值",
        type: "value",
        axisLabel: {
          formatter: "{value}",
          textStyle: {
            color: "rgb(0, 253, 255, 0.6)",
          },
        },
        splitLine: {
          lineStyle: {
            color: "rgb(23, 255, 243, 0.3)",
          },
        },
        axisLine: {
          lineStyle: {
            color: "rgb(0, 253, 255, 0.6)",
          },
        },
      },
      series: lineY,
    };

    // 使用 ECharts 渲染图表
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
      {loading ? (
        <div>Loading...</div>
      ) : (
        <div ref={chartRef} id="chart" style={{ width: "100%", height: "335px" }}></div>
      )}
    </div>
  );
};

export default Horizontal;
