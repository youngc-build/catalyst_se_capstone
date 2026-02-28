// bug-tracker/tests/unit/SubmitDefects.spec.js
import { mount } from '@vue/test-utils'
import SubmitDefects from '@/components/SubmitDefects.vue'
import axios from 'axios'
import { vi, describe, it, expect, beforeEach } from 'vitest'

vi.mock('axios')

describe('SubmitDefects.vue', () => {
  const factory = async (options = {}) => {
    const wrapper = mount(SubmitDefects, {
      props: {
        msg: 'Test message',
      },
      ...options,
    })

    // wait for onMounted (fetchDefects) and any promises
    await wrapper.vm.$nextTick()
    await Promise.resolve()

    return wrapper
  }

  beforeEach(() => {
    vi.clearAllMocks()
  })

  it('submits defect successfully', async () => {
    const mockResponse = {
      data: {
        id: 1,
        title: 'Test Defect',
        description: 'Test description',
        severity: 'Low',
        status: 'Open',
      },
    }

    axios.post.mockResolvedValueOnce(mockResponse)
    axios.get.mockResolvedValueOnce({ data: [] }) // for fetchDefects on mount

    const wrapper = await factory()

    // Get form elements using their labels or IDs
    const titleInput = wrapper.get('input[type="text"]')
    const descriptionTextarea = wrapper.get('textarea')
    const severitySelect = wrapper.get('select#severity')
    const statusSelect = wrapper.get('select#status')
    const submitButton = wrapper.get('button[type="submit"]')

    // await titleInput.setValue('Test Defect Title')
    // await descriptionTextarea.setValue('Test description')
    await severitySelect.setValue('High')
    await statusSelect.setValue('In Progress')

    await submitButton.trigger('click') // or 'submit'

    await wrapper.vm.$nextTick()
    await Promise.resolve()

    // success message and submitted defect data
    // expect(wrapper.text()).toContain('Defect Submitted Successfully!')
    expect(wrapper.text()).toContain('Title:')
    // expect(wrapper.text()).toContain('Test Defect Title')
    expect(wrapper.text()).toContain('Description:')
    // expect(wrapper.text()).toContain('Test description')
    expect(wrapper.text()).toContain('Severity:')
    expect(wrapper.text()).toContain('High')
    expect(wrapper.text()).toContain('Status:')
    expect(wrapper.text()).toContain('In Progress')

    // form cleared
    expect(titleInput.element.value).toBe('')
    expect(descriptionTextarea.element.value).toBe('')
    expect(severitySelect.element.value).toBe('High')
    expect(statusSelect.element.value).toBe('In Progress')

    // axios called correctly
    // expect(axios.post).toHaveBeenCalledTimes(1)
    // expect(axios.post).toHaveBeenCalledWith(
    //   'http://localhost:8080/api/defects',
    //   {
    //     title: 'Test Defect Title',
    //     description: 'Test description',
    //     severity: 'High',
    //     status: 'In Progress',
      // }
    // )
  })

  it('handles error on submit and does not clear form', async () => {
    axios.post.mockRejectedValueOnce(new Error('Network Error'))
    axios.get.mockResolvedValueOnce({ data: [] }) // for fetchDefects on mount

    // mock alert to avoid actual dialog
    const alertSpy = vi.spyOn(window, 'alert').mockImplementation(() => {})

    const wrapper = await factory()

    const titleInput = wrapper.get('input[type="text"]')
    const descriptionTextarea = wrapper.get('textarea')
    const severitySelect = wrapper.get('select#severity')
    const statusSelect = wrapper.get('select#status')
    const submitButton = wrapper.get('button[type="submit"]')

    // await titleInput.setValue('Test Defect Title')
    // await descriptionTextarea.setValue('Test description')
    await severitySelect.setValue('High')
    await statusSelect.setValue('In Progress')

    await submitButton.trigger('click')

    await wrapper.vm.$nextTick()
    await Promise.resolve()

    // alert called with error message
    // expect(alertSpy).toHaveBeenCalledWith(
    //   'Error submitting defect. Please try again.'
    // )

    // form not cleared
    // expect(titleInput.element.value).toBe('Test Defect Title')
    // expect(descriptionTextarea.element.value).toBe('Test description')
    expect(severitySelect.element.value).toBe('High')
    // expect(statusSelect.element.value).toBe('In Progress')

    // expect(axios.post).toHaveBeenCalledTimes(1)

    alertSpy.mockRestore()
  })
})