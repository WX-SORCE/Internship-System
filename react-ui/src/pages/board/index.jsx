/*
 * @Author: 严西娟 499820911@qq.com
 * @Date: 2025-04-02 15:46:07
 * @LastEditors: 严西娟 499820911@qq.com
 * @LastEditTime: 2025-04-02 15:52:52
 * @FilePath: \react-ant-admin\src\pages\board\index.jsx
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
import React, { useState, useEffect, useRef } from "react";
import { Row, Col, Button } from "antd";
import HomeHeader from "./children/homeHeader";
import ModuleItem from "./children/ModuleItem";
// import IndexNum from "./children/indexNum";
import EarthBg from "./children/earthBg";
import LineChart from "./children/manylinechart/lineChart";
import RadarChart from "./children/manylinechart/RadarChart";
import Histogram from "./children/manylinechart/Histogram";
import Histogram2 from "./children/manylinechart/Histogram2";

import PieChart from "./children/manylinechart/PieChart";
// import ScrollChart from "./children/manylinechart/scroll";
import Horizontal from "./children/manylinechart/Horizontal";
// import {
//   getEleQualitylineIdNew,
//   getScoreproId,
//   economicAnalysislineIdNew,
//   safetyRunlineIdNew,
//   eventStatisticproId,
//   rollboardproId,
//   showEstimate,
// } from "@/api/LOACalculation/calculationapi.js";
import "./index.less";
const LOAbigboard = () => {
  const [initLoading, setInitLoading] = useState(false);
  // const [initData, setInitData] = useState({});
  // const [linesonmed, setLinesonmed] = useState([]);
  // const [Radarsonmed, setRadarsonmed] = useState({});
  // const [Histogramsonmed, setHistogramsonmed] = useState({});
  // const [Horizontalsonmed, setHorizontalsonmed] = useState({});
  // const [rowData, setRowData] = useState({});
  // const [PieChartsonmed, setPieChartsonmed] = useState([]);
  // const [rollboardsonmed, setRollboardsonmed] = useState([]);
  // const cachedrowData = JSON.parse(localStorage.getItem("cachedrowData"));

  // useEffect(() => {
  //   const handleBeforeUnload = () => {
  //     saveCachedData();
  //   };
  //   window.addEventListener("beforeunload", handleBeforeUnload);
  //   return () => {
  //     window.removeEventListener("beforeunload", handleBeforeUnload);
  //   };
  // }, []);

  useEffect(() => {
    setTimeout(() => {
      setInitLoading(false);
    }, 2000);
  }, []);

  // useEffect(() => {
  //   console.log("linesonmed改变");
  //   window.dispatchEvent(new CustomEvent("getTransCharts1", { detail: linesonmed }));
  //   console.log("传递");
  // }, [linesonmed]);

  // useEffect(() => {
  //   console.log("Radarsonmed改变", Radarsonmed);
  //   window.dispatchEvent(new CustomEvent("getTransCharts2", { detail: Radarsonmed }));
  //   console.log("传递");
  // }, [Radarsonmed]);

  // useEffect(() => {
  //   console.log("Histogramsonmed改变");
  //   window.dispatchEvent(new CustomEvent("getTransCharts3", { detail: Histogramsonmed }));
  //   console.log("传递");
  // }, [Histogramsonmed]);

  // useEffect(() => {
  //   console.log("Horizontalsonmed", Horizontalsonmed);
  //   window.dispatchEvent(new CustomEvent("getTransCharts4", { detail: Horizontalsonmed }));
  //   console.log("传递");
  // }, [Horizontalsonmed]);

  // useEffect(() => {
  //   console.log("PieChartsonmed改变");
  //   window.dispatchEvent(new CustomEvent("getTransCharts5", { detail: PieChartsonmed }));
  //   console.log("传递");
  // }, [PieChartsonmed]);

  // useEffect(() => {
  //   console.log("rollboardsonmed改变");
  //   window.dispatchEvent(new CustomEvent("getTransCharts6", { detail: rollboardsonmed }));
  //   console.log("传递");
  // }, [rollboardsonmed]);

  // useEffect(() => {
  //   console.log("选中计划");
  //   console.log("newLineId", rowData);
  //   if (rowData) {
  //     getEleQualitylineIdNew(rowData.lineIdNew).then((res) => {
  //       setLinesonmed(res.rows);
  //     });
  //     getScoreproId(rowData.proId).then((res) => {
  //       setRadarsonmed(res);
  //     });
  //     economicAnalysislineIdNew(rowData.lineIdNew).then((res) => {
  //       setHistogramsonmed(res);
  //     });
  //     safetyRunlineIdNew(rowData.lineIdNew).then((res) => {
  //       setHorizontalsonmed(res);
  //     });
  //     eventStatisticproId(rowData.proId).then((res) => {
  //       setPieChartsonmed(res.data);
  //     });
  //     rollboardproId(rowData.proId).then((res) => {
  //       setRollboardsonmed(res.data);
  //     });
  //   }
  // }, [rowData]);

  // useEffect(() => {
  //   console.log("this.getrowData ", cachedrowData);
  //   if (cachedrowData && typeof cachedrowData === "object" && Object.keys(cachedrowData).length > 0) {
  //     console.log("从缓存中获取数据");
  //     setRowData(cachedrowData);
  //   }
  // }, [cachedrowData]);

  // useEffect(() => {
  //   const searchdom = document.getElementsByClassName("search");
  //   if (searchdom.length > 0) {
  //     Array.from(searchdom).forEach((element) => {
  //       const inputElement = element.querySelector("input");
  //       console.log("inputElement", inputElement.value);
  //       if (inputElement.value.trim() !== "") {
  //         inputElement.style.width = "250px";
  //       }
  //     });
  //   } else {
  //     console.error('未找到具有类名为 "search" 的元素。');
  //   }
  // }, []);

  // const saveCachedData = useCallback(() => {
  //   console.log("页面退出 保存数据", rowData);
  //   localStorage.setItem("cachedrowData", JSON.stringify(rowData));
  // }, [rowData]);
  const targetRef = useRef(null);
  const [isFullscreen, setIsFullscreen] = useState(false);

  // 检查浏览器是否支持全屏 API
  const isFullscreenSupported = 'fullscreenEnabled' in document ||
    'mozFullScreenEnabled' in document ||
    'webkitFullscreenEnabled' in document ||
    'msFullscreenEnabled' in document;

  // 进入全屏
  const requestFullscreen = () => {
    const element = targetRef.current;
    if (element) {
      if (element.requestFullscreen) {
        element.requestFullscreen();
      } else if (element.mozRequestFullScreen) {
        element.mozRequestFullScreen();
      } else if (element.webkitRequestFullscreen) {
        element.webkitRequestFullscreen();
      } else if (element.msRequestFullscreen) {
        element.msRequestFullscreen();
      }
    }
  };

  // 退出全屏
  const exitFullscreen = () => {
    if (document.exitFullscreen) {
      document.exitFullscreen();
    } else if (document.mozCancelFullScreen) {
      document.mozCancelFullScreen();
    } else if (document.webkitExitFullscreen) {
      document.webkitExitFullscreen();
    } else if (document.msExitFullscreen) {
      document.msExitFullscreen();
    }
  };

  // 监听全屏状态变化
  useEffect(() => {
    const handleFullscreenChange = () => {
      setIsFullscreen(!!document.fullscreenElement);
    };

    document.addEventListener('fullscreenchange', handleFullscreenChange);
    document.addEventListener('mozfullscreenchange', handleFullscreenChange);
    document.addEventListener('webkitfullscreenchange', handleFullscreenChange);
    document.addEventListener('msfullscreenchange', handleFullscreenChange);

    return () => {
      document.removeEventListener('fullscreenchange', handleFullscreenChange);
      document.removeEventListener('mozfullscreenchange', handleFullscreenChange);
      document.removeEventListener('webkitfullscreenchange', handleFullscreenChange);
      document.removeEventListener('msfullscreenchange', handleFullscreenChange);
    };
  }, []);
  return (
    <div className="home" ref={targetRef}>
      <div className="chart-list">
        <HomeHeader />
        {isFullscreenSupported && !isFullscreen && (
          <Button type="primary" ghost style={{
            position: "absolute",
            top: "30px",
            left: " 20px",

          }}
            onClick={requestFullscreen}>进入全屏</Button>
        )}
        <div style={{ padding: "0 8px" }} className="chart-content" >
          <Row gutter={8} className="chart-content-row">
            <Col className="chart-content-col" span={8}>
              <Row className="chart-content-left" style={{ display: "flex" }}>
                <Col className="chart-content-left-item" span={24}>
                  <ModuleItem title="方案占比" >
                    <PieChart />
                  </ModuleItem>
                </Col>
                <Col className="chart-content-left-item" span={24}>
                  <ModuleItem title="方案价值">
                    <Horizontal />
                  </ModuleItem>
                </Col>
              </Row>
            </Col>
            <Col className="chart-content-col" span={8}>
              <Row className="chart-content-center" style={{ display: "flex" }}>
                <Col className="chart-content-center-item" span={24}>
                  <ModuleItem>

                    <RadarChart />

                  </ModuleItem>
                </Col>
                <Col className="chart-content-center-item" span={24}>
                  <ModuleItem title="历史趋势">
                    {/* <ScrollChart />
                     */}
                    <LineChart />

                  </ModuleItem>
                </Col>
              </Row>
            </Col>
            <Col className="chart-content-col" span={8}>
              <Row className="chart-content-right" style={{ display: "flex" }}>
                <Col className="chart-content-right-item" span={24}>
                  <ModuleItem title="风险评估">
                    {/* Histogram */}
                    <Histogram />

                  </ModuleItem>
                </Col>
                <Col className="chart-content-right-item" span={24}>
                  <ModuleItem title="收益评估">
                    {/* <PieChart /> */}
                    <Histogram2 />


                  </ModuleItem>
                </Col>
              </Row>
            </Col>
          </Row>
          <EarthBg />
        </div>
      </div>
    </div>
  );
};
LOAbigboard.route = { path: "/board" };
export default LOAbigboard;