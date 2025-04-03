import React, { useEffect, useState } from 'react';
import dayjs from 'dayjs';
import './HomeHeader.less';

const HomeHeader = ({ store }) => {
  const [time, setTime] = useState('--');
  const [date, setDate] = useState('--');
  const [week, setWeek] = useState('--');

  useEffect(() => {
    const intervalId = setInterval(getTime, 1000);
    return () => clearInterval(intervalId);
  }, []);

  const getTime = () => {
    const weekdaysMap = {
      Monday: '星期一',
      Tuesday: '星期二',
      Wednesday: '星期三',
      Thursday: '星期四',
      Friday: '星期五',
      Saturday: '星期六',
      Sunday: '星期日'
    };
    const currentWeekday = dayjs().format('dddd');
    const translatedWeekday = weekdaysMap[currentWeekday];
    setWeek(translatedWeekday);
    setTime(dayjs().format('HH:mm:ss'));
    setDate(dayjs().format('YYYY/MM/DD'));
  };

  return (
    <div className="home-header">
      {/* <div style={{ width: '237px', paddingRight: '177px', zIndex: 1000 }}> */}
      <div class="search"></div>
      <div className="home-header-title">投资组合分析大屏</div>
      <div className="home-header-time">
        <span className="week-value">{week}</span>
        <span className="date-value">{date}</span>
        <span className="time-value">{time}</span>
      </div>
    </div>
    // </div>
  );
};

export default HomeHeader;