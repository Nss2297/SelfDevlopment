/** @type {import('tailwindcss').Config} */
module.exports = {
  darkMode: 'dark-mode-disabled',
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    colors: {},
    screens: {
      sm: '576px',
      md: '768px',
      lg: '992px',
      xl: '1200px',
      'xxl': '1400px'
    },
    fontFamily: {
      sans: ['Expo Arabic', 'sans-serif'],
    },
    spacing: {
      0: '0',
      1: '1px',
      2: '2px',
      4: '4px',
      6: '6px',
      8: '8px',
      10: '10px',
      12: '12px',
      14: '14px',
      16: '16px',
      20: '20px',
      24: '24px',
      32: '32px',
      40: '40px',
      48: '48px',
      56: '56px',
      64: '64px',
      72: '72px',
      80: '80px',
      88: '88px',
      96: '96px',
    },
    extend: {
      colors: {
        'text': {
          DEFAULT: '#1a1b1f'
        },
        'secondary': {
          DEFAULT: '#46464a'
        },
        'tertiary': {
          DEFAULT: '#77777a'
        },
        'light-grey': {
          DEFAULT: '#f2f0f4'
        },
        'border': {
          DEFAULT: '#c7c6ca'
        },
        'light-border': {
          DEFAULT: '#e1e1e1'
        },
        "white": {
          primary: '#f2f0f4',
          DEFAULT: "#fff"
        },
        "transparent": {
          DEFAULT: "rgba(0,0,0,0)"
        },
        'primary': {
          900: '#011733',
          800: '#012e66',
          700: '#024599',
          600: '#025ccc',
          500: '#0373ff',
          400: '#358fff',
          300: '#68abff',
          200: '#9ac7ff',
          100: '#cde3ff',
          50: '#e6f1ff',
          25: '#f3f8ff',
          DEFAULT: '#0373ff'
        },
        'neutral': {
          900: '#1a1b1f',
          800: '#2f3033',
          700: '#46464a',
          600: '#5e5e62',
          500: '#77777a',
          400: '#919094',
          300: '#ababaf',
          200: '#c7c6ca',
          100: '#e3e2e6',
          50: '#F2F0F4',
          25: '#FAF9FD',
        },
        'error': {
          900: '#30090c',
          800: '#601217',
          700: '#901b23',
          600: '#c0242e',
          500: '#f02d3a',
          400: '#f35761',
          300: '#f68189',
          200: '#f9abb0',
          100: '#fcd5d8',
          50: '#feeaeb',
          25: '#fff4f5',
          DEFAULT: '#f02d3a',
        },
        'success': {
          900: '#0c270f',
          800: '#184d1e',
          700: '#23742c',
          600: '#2f9a3b',
          500: '#3bc14a',
          400: '#62cd6e',
          300: '#89da92',
          200: '#b1e6b7',
          100: '#d8f3db',
          50: '#ebf9ed',
          25: '#f5fcf6',
          DEFAULT: '#3bc14a'
        },
        'warning': {
          900: '#312104',
          800: '#614307',
          700: '#92640b',
          600: '#c2860e',
          500: '#f3a712',
          400: '#f5b941',
          300: '#f8ca71',
          200: '#fadca0',
          100: '#fdedd0',
          50: '#fef6e7',
          25: '#fffbf3',
          DEFAULT: '#f3a712'
        }
      }
    }
  },
  plugins: [],
}
