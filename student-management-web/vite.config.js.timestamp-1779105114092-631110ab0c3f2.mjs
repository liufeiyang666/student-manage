// vite.config.js
import { defineConfig } from "file:///C:/Users/%E4%B8%A5%E7%9B%8A%E5%A0%83/Desktop/%E6%B5%8B%E8%AF%95springboot%E9%A1%B9%E7%9B%AE/student-management-web/node_modules/vite/dist/node/index.js";
import vue from "file:///C:/Users/%E4%B8%A5%E7%9B%8A%E5%A0%83/Desktop/%E6%B5%8B%E8%AF%95springboot%E9%A1%B9%E7%9B%AE/student-management-web/node_modules/@vitejs/plugin-vue/dist/index.mjs";
import { fileURLToPath, URL } from "node:url";
var __vite_injected_original_import_meta_url = "file:///C:/Users/%E4%B8%A5%E7%9B%8A%E5%A0%83/Desktop/%E6%B5%8B%E8%AF%95springboot%E9%A1%B9%E7%9B%AE/student-management-web/vite.config.js";
var vite_config_default = defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", __vite_injected_original_import_meta_url))
    }
  },
  server: {
    port: 3e3,
    proxy: {
      "/api": {
        target: "http://localhost:8080",
        changeOrigin: true
      }
    }
  }
});
export {
  vite_config_default as default
};
//# sourceMappingURL=data:application/json;base64,ewogICJ2ZXJzaW9uIjogMywKICAic291cmNlcyI6IFsidml0ZS5jb25maWcuanMiXSwKICAic291cmNlc0NvbnRlbnQiOiBbImNvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9kaXJuYW1lID0gXCJDOlxcXFxVc2Vyc1xcXFxcdTRFMjVcdTc2Q0FcdTU4MDNcXFxcRGVza3RvcFxcXFxcdTZENEJcdThCRDVzcHJpbmdib290XHU5ODc5XHU3NkVFXFxcXHN0dWRlbnQtbWFuYWdlbWVudC13ZWJcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfZmlsZW5hbWUgPSBcIkM6XFxcXFVzZXJzXFxcXFx1NEUyNVx1NzZDQVx1NTgwM1xcXFxEZXNrdG9wXFxcXFx1NkQ0Qlx1OEJENXNwcmluZ2Jvb3RcdTk4NzlcdTc2RUVcXFxcc3R1ZGVudC1tYW5hZ2VtZW50LXdlYlxcXFx2aXRlLmNvbmZpZy5qc1wiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9pbXBvcnRfbWV0YV91cmwgPSBcImZpbGU6Ly8vQzovVXNlcnMvJUU0JUI4JUE1JUU3JTlCJThBJUU1JUEwJTgzL0Rlc2t0b3AvJUU2JUI1JThCJUU4JUFGJTk1c3ByaW5nYm9vdCVFOSVBMSVCOSVFNyU5QiVBRS9zdHVkZW50LW1hbmFnZW1lbnQtd2ViL3ZpdGUuY29uZmlnLmpzXCI7aW1wb3J0IHsgZGVmaW5lQ29uZmlnIH0gZnJvbSAndml0ZSdcbmltcG9ydCB2dWUgZnJvbSAnQHZpdGVqcy9wbHVnaW4tdnVlJ1xuaW1wb3J0IHsgZmlsZVVSTFRvUGF0aCwgVVJMIH0gZnJvbSAnbm9kZTp1cmwnXG5cbmV4cG9ydCBkZWZhdWx0IGRlZmluZUNvbmZpZyh7XG4gIHBsdWdpbnM6IFt2dWUoKV0sXG4gIHJlc29sdmU6IHtcbiAgICBhbGlhczoge1xuICAgICAgJ0AnOiBmaWxlVVJMVG9QYXRoKG5ldyBVUkwoJy4vc3JjJywgaW1wb3J0Lm1ldGEudXJsKSlcbiAgICB9XG4gIH0sXG4gIHNlcnZlcjoge1xuICAgIHBvcnQ6IDMwMDAsXG4gICAgcHJveHk6IHtcbiAgICAgICcvYXBpJzoge1xuICAgICAgICB0YXJnZXQ6ICdodHRwOi8vbG9jYWxob3N0OjgwODAnLFxuICAgICAgICBjaGFuZ2VPcmlnaW46IHRydWVcbiAgICAgIH1cbiAgICB9XG4gIH1cbn0pIl0sCiAgIm1hcHBpbmdzIjogIjtBQUFvYSxTQUFTLG9CQUFvQjtBQUNqYyxPQUFPLFNBQVM7QUFDaEIsU0FBUyxlQUFlLFdBQVc7QUFGb00sSUFBTSwyQ0FBMkM7QUFJeFIsSUFBTyxzQkFBUSxhQUFhO0FBQUEsRUFDMUIsU0FBUyxDQUFDLElBQUksQ0FBQztBQUFBLEVBQ2YsU0FBUztBQUFBLElBQ1AsT0FBTztBQUFBLE1BQ0wsS0FBSyxjQUFjLElBQUksSUFBSSxTQUFTLHdDQUFlLENBQUM7QUFBQSxJQUN0RDtBQUFBLEVBQ0Y7QUFBQSxFQUNBLFFBQVE7QUFBQSxJQUNOLE1BQU07QUFBQSxJQUNOLE9BQU87QUFBQSxNQUNMLFFBQVE7QUFBQSxRQUNOLFFBQVE7QUFBQSxRQUNSLGNBQWM7QUFBQSxNQUNoQjtBQUFBLElBQ0Y7QUFBQSxFQUNGO0FBQ0YsQ0FBQzsiLAogICJuYW1lcyI6IFtdCn0K
