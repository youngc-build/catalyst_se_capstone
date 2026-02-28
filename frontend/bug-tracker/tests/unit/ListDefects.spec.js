import DefectsList from '@/components/ListDefects.vue'
import { mount } from '@vue/test-utils'
import axios from 'axios'
import { vi, describe, it, expect, beforeEach } from 'vitest'
// import DefectsList from '@/components/DefectsList.vue'

vi.mock('axios')

describe('DefectsList.vue', () => {
  const factory = async (options = {}) => {
    if (!axios.get.mockImplementation) {
      axios.get.mockResolvedValue({ data: [] })
    }

    const wrapper = mount(DefectsList, {
      props: {
        msg: 'Test message',
      },
      ...options,
    })

    await wrapper.vm.$nextTick()
    await Promise.resolve()

    return wrapper
  }

  beforeEach(() => {
    vi.clearAllMocks()
  })

  it('calls the defects API on mount', async () => {
    axios.get.mockResolvedValueOnce({ data: [] })

    await factory()

    expect(axios.get).toHaveBeenCalledTimes(1)
    expect(axios.get).toHaveBeenCalledWith('http://localhost:8080/api/defects')
  })

  it('shows "No defects found." when API returns empty array', async () => {
    axios.get.mockResolvedValueOnce({ data: [] })

    const wrapper = await factory()

    expect(wrapper.text()).toContain('No defects found.')
    expect(wrapper.find('.defects-list').exists()).toBe(false)
  })

  it('renders a list of defects when API returns data', async () => {
    const mockDefects = [
      {
        id: 1,
        title: 'Defect 1',
        description: 'Description 1',
        severity: 'High',
        status: 'Open',
      },
      {
        id: 2,
        title: 'Defect 2',
        description: 'Description 2',
        severity: 'Low',
        status: 'Closed',
      },
    ]

    axios.get.mockResolvedValueOnce({ data: mockDefects })

    const wrapper = await factory()

    const list = wrapper.find('.defects-list')
    expect(list.exists()).toBe(true)

    const items = list.findAll('li')
    expect(items).toHaveLength(mockDefects.length)

    const first = items[0].text()
    expect(first).toContain('Defect 1')
    expect(first).toContain('Description 1')
    expect(first).toContain('High')
    expect(first).toContain('Open')
  })

  it('logs an error if the API call fails', async () => {
    const consoleSpy = vi.spyOn(console, 'error').mockImplementation(() => {})
    axios.get.mockRejectedValueOnce(new Error('Network error'))

    const wrapper = await factory()

    expect(wrapper.text()).toContain('No defects found.')
    expect(consoleSpy).toHaveBeenCalled()

    consoleSpy.mockRestore()
  })
})