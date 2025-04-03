// 用于缓存函数，避免在组件重新渲染时不必要的函数重新创建
import { useCallback } from "react";
// useDispatch：用于获取 Redux 的 dispatch 方法，触发 action
// useSelector：用于从 Redux store 中提取状态
import { useDispatch, useSelector } from "react-redux";
// 这是一个 action creator，用于创建修改布局模式的 action
import { changeLayoutMode } from "../action";
// 这是一个 selector 函数，用于从 Redux store 中获取布局模式的状态
import { getLayoutMode } from "../getters";

// 自定义 Hook：useStateLayout
// 用于从 Redux store 中获取当前的布局模式状态
export const useStateLayout = () => useSelector(getLayoutMode);

// 自定义 Hook：useDispatchLayout
// 用于封装修改布局模式的逻辑，返回一个可调用的方法
export function useDispatchLayout() {
  // 获取 Redux 的 dispatch 方法
  const dispatch = useDispatch();

  // 使用 useCallback 缓存 stateChangeLayout 函数
  // 该函数接收两个参数：type 和 mode，并调用 dispatch(changeLayoutMode(type, mode)) 来触发布局模式的更新
  // [dispatch] 是 useCallback 的依赖项，确保 dispatch 不变时函数不会重新创建
  const stateChangeLayout = useCallback(
    (type, mode) => dispatch(changeLayoutMode(type, mode)),
    [dispatch]
  );

  // 返回一个包含 stateChangeLayout 方法的对象
  return {
    stateChangeLayout,
  };
}