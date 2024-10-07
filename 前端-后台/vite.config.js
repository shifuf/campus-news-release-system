import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import VueJsx from '@vitejs/plugin-vue-jsx'
// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    VueJsx(),

  ],
  server:{
    proxy:{
      '/api':{
        target:'http://localhost:8080',
        changeOrigin:true,
        rewrite:(path)=>path.replace(/^\/api/,'')
      }
    }
  }
})
