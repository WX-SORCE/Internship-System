import React, { useState, useEffect } from "react";
// import gsap from "gsap";
// import "./index-num.less"; 

const IndexNum = ({ initData }) => {
  const [renderOpen, setRenderOpen] = useState(0);
  const [renderGit, setRenderGit] = useState(0);
  const [sonmed, setSonmed] = useState({});

  useEffect(() => {
    const handleGetTransCharts = (event) => {
      const res = event.detail;
      setSonmed(res);
      console.log("总分接收", sonmed);
    };
    window.addEventListener("getTransCharts2", handleGetTransCharts);
    return () => {
      window.removeEventListener("getTransCharts2", handleGetTransCharts);
    };
  }, []);

  // useEffect(() => {
  //   // 这里根据initData更新状态值，使用gsap实现动画效果
  //   gsap.to({ value: renderOpen }, { duration: 1, value: +initData.openRank, onUpdate: () => setRenderOpen(({ value }) => value) });
  //   gsap.to({ value: renderGit }, { duration: 1, value: +initData.gitHub, onUpdate: () => setRenderGit(({ value }) => value) });
  // }, [initData]);

  return (
    <div className="index-num">
      <div className="index-row-value">
        <div className="index-col">{sonmed.total}</div>
        <div className="index-divider"></div>
        {/* <div className="index-col">{ 4564 }</div> */}
      </div>
      <div className="index-row-label">
        <div className="index-col">总分</div>
        {/* <div className="index-col">GitHub平均值</div> */}
      </div>
    </div>
  );
};

export default IndexNum;